package com.test.translator_kmm.di

import android.content.Context
import com.test.translator_kmm.database.TranslateDatabase
import com.test.translator_kmm.translate.data.history.SqlDelightHistoryDataSource
import com.test.translator_kmm.translate.data.local.DatabaseDriverFactory
import com.test.translator_kmm.translate.data.remote.HttpClientFactory
import com.test.translator_kmm.translate.data.translate.KtorTranslateClient
import com.test.translator_kmm.translate.domain.history.HistoryDataSource
import com.test.translator_kmm.translate.domain.translate.Translate
import com.test.translator_kmm.translate.domain.translate.TranslateClient
import io.ktor.client.*
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual fun platformModule(): Module {
    return module {
        single { createDatabase(get()) }
        single { createHttpClient() }
        single<HistoryDataSource> { SqlDelightHistoryDataSource(get()) }
        single<TranslateClient> { KtorTranslateClient(get()) }
        singleOf(::Translate)
    }
}

private fun createDatabase(context: Context): TranslateDatabase {
    val sqlDriver = DatabaseDriverFactory(context).create()
    return TranslateDatabase(sqlDriver)
}

private fun createHttpClient(): HttpClient {
    return HttpClientFactory().create()
}

