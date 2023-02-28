package com.test.translator_kmm.translate.domain.history

import kotlinx.coroutines.flow.Flow

interface HistoryDataSource {
    fun getHistory(): Flow<List<HistoryItem>>
    suspend fun insertHistoryItem(historyItem: HistoryItem)
}