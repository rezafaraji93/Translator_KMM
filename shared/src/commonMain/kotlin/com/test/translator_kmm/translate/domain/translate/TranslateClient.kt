package com.test.translator_kmm.translate.domain.translate

import com.test.translator_kmm.core.domain.language.Language

interface TranslateClient {
    suspend fun translate(
        fromlanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String
}