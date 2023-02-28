package com.test.translator_kmm.di

import com.test.translator_kmm.database.TranslateDatabase
import com.test.translator_kmm.translate.data.history.SqlDelightHistoryDataSource
import com.test.translator_kmm.translate.data.local.DatabaseDriverFactory
import com.test.translator_kmm.translate.data.remote.HttpClientFactory
import com.test.translator_kmm.translate.data.translate.KtorTranslateClient
import com.test.translator_kmm.translate.domain.history.HistoryDataSource
import com.test.translator_kmm.translate.domain.translate.Translate
import com.test.translator_kmm.translate.domain.translate.TranslateClient
import io.ktor.client.*
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module {
    return module {
        single { createDatabase() }
        single { createHttpClient() }
        single<HistoryDataSource> { SqlDelightHistoryDataSource(get()) }
        single<TranslateClient> { KtorTranslateClient(get()) }
        single { Translate(get(), get()) }
    }
}

private fun createDatabase(): TranslateDatabase {
    val sqlDriver = DatabaseDriverFactory().create()
    return TranslateDatabase(sqlDriver)
}

private fun createHttpClient(): HttpClient {
    return HttpClientFactory().create()
}

fun initKoin() {
    startKoin {
        modules(appModule())
    }
}