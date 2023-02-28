package com.test.translator_kmm.di

import org.koin.core.module.Module

expect fun platformModule(): Module

fun appModule() = listOf(platformModule())