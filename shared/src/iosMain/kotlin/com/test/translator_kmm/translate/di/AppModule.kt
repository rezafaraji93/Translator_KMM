package com.test.translator_kmm.translate.di

import com.test.translator_kmm.database.TranslateDatabase
import com.test.translator_kmm.translate.data.history.SqlDelightHistoryDataSource
import com.test.translator_kmm.translate.data.local.DatabaseDriverFactory
import com.test.translator_kmm.translate.data.remote.HttpClientFactory
import com.test.translator_kmm.translate.data.translate.KtorTranslateClient
import com.test.translator_kmm.translate.domain.history.HistoryDataSource
import com.test.translator_kmm.translate.domain.translate.Translate
import com.test.translator_kmm.translate.domain.translate.TranslateClient

class AppModule {
    val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(TranslateDatabase(DatabaseDriverFactory().create()))
    }

    private val translateClient: TranslateClient by lazy {
        KtorTranslateClient(HttpClientFactory().create())
    }

    val translateUseCase: Translate by lazy {
        Translate(translateClient, historyDataSource)
    }
}