package com.test.translator_kmm.translate.data.translate

import com.test.translator_kmm.NetworkConstants
import com.test.translator_kmm.core.domain.language.Language
import com.test.translator_kmm.translate.domain.translate.TranslateClient
import com.test.translator_kmm.translate.domain.translate.TranslateError
import com.test.translator_kmm.translate.domain.translate.TranslateException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*

class KtorTranslateClient(
    private val httpClient: HttpClient
) : TranslateClient {

    override suspend fun translate(
        fromlanguage: Language, fromText: String, toLanguage: Language
    ): String {
        val result = try {
            httpClient.post(NetworkConstants.BASE_URL + "translate") {
                contentType(ContentType.Application.Json)
                setBody(
                    TranslateDto(
                        textToTranslate = fromText,
                        sourceLanguageCode = fromlanguage.langCode,
                        targetLanguageCode = toLanguage.langCode
                    )
                )

            }
        } catch (e: IOException) {
            e.printStackTrace()
            throw TranslateException(TranslateError.SERVICE_UNAVAILABLE)
        }
        when (result.status.value) {
            in 200..299 -> Unit
            500 -> throw TranslateException(TranslateError.SERVER_ERROR)
            in 400..499 -> throw TranslateException(TranslateError.CLIENT_ERROR)
            else -> throw TranslateException(TranslateError.UNKNOWN_ERROR)
        }

        return try {
            result.body<TranslatedDto>().translatedText
        } catch (e: Exception) {
            throw TranslateException(TranslateError.SERVER_ERROR)
        }
    }
}