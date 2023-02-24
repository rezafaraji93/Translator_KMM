package com.test.translator_kmm.core.domain.util

import kotlinx.coroutines.flow.StateFlow

actual open class CommonStateFlow<T> actual constructor(
    private val flow: StateFlow<T>
) : StateFlow<T> by flow