package com.test.translator_kmm.android.di

import com.test.translator_kmm.translate.presentation.TranslateViewModelShared
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { TranslateViewModelShared() }
}