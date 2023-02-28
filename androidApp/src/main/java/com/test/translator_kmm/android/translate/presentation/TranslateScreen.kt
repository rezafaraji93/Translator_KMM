package com.test.translator_kmm.android.translate.presentation

import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.test.translator_kmm.android.R
import com.test.translator_kmm.android.core.presentation.Routes
import com.test.translator_kmm.android.translate.presentation.components.*
import com.test.translator_kmm.translate.domain.translate.TranslateError
import com.test.translator_kmm.translate.presentation.TranslateEvent
import com.test.translator_kmm.translate.presentation.TranslateState
import com.test.translator_kmm.translate.presentation.TranslateViewModelShared
import org.koin.androidx.compose.koinViewModel
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TranslateScreen(
    state: TranslateState, onEvent: (TranslateEvent) -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = state.error) {
        val message = when (state.error) {
            TranslateError.SERVICE_UNAVAILABLE -> context.getString(R.string.errorServiceUnavailable)
            TranslateError.CLIENT_ERROR -> context.getString(R.string.clienError)
            TranslateError.SERVER_ERROR -> context.getString(R.string.serverError)
            TranslateError.UNKNOWN_ERROR -> context.getString(R.string.unknownError)
            null -> null
        }

        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            onEvent(TranslateEvent.OnErrorSeen)
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { onEvent(TranslateEvent.RecordAudio) },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            modifier = Modifier.size(75.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.mic),
                contentDescription = stringResource(
                    id = R.string.recordAudio
                )
            )
        }
    }, floatingActionButtonPosition = FabPosition.Center) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LanguageDropDown(language = state.fromLanguage,
                        isOpen = state.isChoosingFromLanguage,
                        onClick = { onEvent(TranslateEvent.OpenFromLanguageDropDown) },
                        onDismiss = { onEvent(TranslateEvent.StopChoosingLanguage) },
                        onSelectLanguage = { onEvent(TranslateEvent.ChooseFromLanguage(it)) })

                    Spacer(modifier = Modifier.weight(1f))

                    SwapLanguagesButton(onClick = { onEvent(TranslateEvent.SwapLanguages) })

                    Spacer(modifier = Modifier.weight(1f))

                    LanguageDropDown(language = state.toLanguage,
                        isOpen = state.isChoosingToLanguage,
                        onClick = { onEvent(TranslateEvent.OpenToLanguageDropDown) },
                        onDismiss = { onEvent(TranslateEvent.StopChoosingLanguage) },
                        onSelectLanguage = { onEvent(TranslateEvent.ChooseToLanguage(it)) })
                }
            }

            item {

                val clipboardManager = LocalClipboardManager.current
                val keyboardController = LocalSoftwareKeyboardController.current
                val tts = rememberTextToSpeech()

                TranslateTextField(
                    fromText = state.fromText,
                    toText = state.toText,
                    isTranslating = state.isTranslating,
                    fromLanguage = state.fromLanguage,
                    toLanguage = state.toLanguage,
                    onTranslateClick = {
                        keyboardController?.hide()
                        onEvent(TranslateEvent.Translate)
                    },
                    onTextChange = { onEvent(TranslateEvent.ChangeTranslationText(it)) },
                    onCopyClick = { text ->
                        clipboardManager.setText(buildAnnotatedString {
                            append(
                                text
                            )
                        })
                        Toast.makeText(
                            context,
                            context.getString(R.string.copiedToClipboard),
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    onCloseClick = { onEvent(TranslateEvent.CloseTranslation) },
                    onSpeakerClick = {
                        tts.language = state.toLanguage.toLocale() ?: Locale.ENGLISH
                        tts.speak(
                            state.toText, TextToSpeech.QUEUE_FLUSH, null, null
                        )
                    },
                    onTextFieldClick = {
                        onEvent(TranslateEvent.EditTranslation)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                if (state.history.isNotEmpty()) {
                    Text(
                        text = stringResource(id = R.string.history),
                        style = MaterialTheme.typography.h2
                    )
                }
            }


            items(state.history) { history ->
                TranslateHistoryItem(
                    item = history,
                    onClick = { onEvent(TranslateEvent.SelectHistoryItem(history)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item { Spacer(modifier = Modifier.height(90.dp)) }
        }
    }


}

@Composable
fun TranslateRoot() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.TRANSLATE) {

        composable(Routes.TRANSLATE) {
            val viewModel: TranslateViewModelShared = koinViewModel()
            val state by viewModel.state.collectAsState()
            TranslateScreen(state = state, onEvent = { event ->
                when (event) {
                    is TranslateEvent.RecordAudio -> navController.navigate(Routes.VOICE_TO_TEXT + "/${state.fromLanguage.language.langCode}")
                    else -> viewModel.onEvent(event)
                }
            })
        }

        composable(Routes.VOICE_TO_TEXT + "/{languageCode}",
            arguments = listOf(navArgument("languageCode") {
                type = NavType.StringType
                defaultValue = "en"
            })) {
            Text(text = "Voice to Text")
        }

    }
}