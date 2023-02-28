package com.test.translator_kmm.android

import android.app.Application
import com.test.translator_kmm.android.di.appModule
import com.test.translator_kmm.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TranslateApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TranslateApp)
            modules(appModule + platformModule())
        }
    }

}