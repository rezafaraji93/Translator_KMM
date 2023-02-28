package com.test.translator_kmm.translate.data.history

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.test.translator_kmm.database.TranslateDatabase
import com.test.translator_kmm.translate.domain.history.HistoryDataSource
import com.test.translator_kmm.translate.domain.history.HistoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent

class SqlDelightHistoryDataSource(
    db: TranslateDatabase
) : HistoryDataSource, KoinComponent {

    private val queries = db.translateQueries


    override fun getHistory(): Flow<List<HistoryItem>> {
        return queries.getHistory().asFlow().mapToList(Dispatchers.Default).map { history ->
                history.map { it.toHistoryItem() }
            }
    }

    override suspend fun insertHistoryItem(historyItem: HistoryItem) {
        queries.insertHistoryEntity(
            id = historyItem.id,
            fromLanguageCode = historyItem.fromLanguageCode,
            fromText = historyItem.fromText,
            toText = historyItem.toText,
            toLanguageCode = historyItem.toLanguageCode,
            timestampt = Clock.System.now().toEpochMilliseconds()
        )
    }
}