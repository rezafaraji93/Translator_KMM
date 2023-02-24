package com.test.translator_kmm.android.di

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import com.test.translator_kmm.database.TranslateDatabase
import com.test.translator_kmm.translate.data.history.SqlDelightHistoryDataSource
import com.test.translator_kmm.translate.data.local.DatabaseDriverFactory
import com.test.translator_kmm.translate.data.remote.HttpClientFactory
import com.test.translator_kmm.translate.data.translate.KtorTranslateClient
import com.test.translator_kmm.translate.domain.history.HistoryDataSource
import com.test.translator_kmm.translate.domain.translate.Translate
import com.test.translator_kmm.translate.domain.translate.TranslateClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun provideTranslateClient(httpClient: HttpClient): TranslateClient {
        return KtorTranslateClient(httpClient)
    }


    @Provides
    @Singleton
    fun provideDatabaseDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun provideHistoryDataSource(driver: SqlDriver): HistoryDataSource {
        return SqlDelightHistoryDataSource(TranslateDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(client: TranslateClient, dataSource: HistoryDataSource): Translate {
        return Translate(client, dataSource)
    }

}