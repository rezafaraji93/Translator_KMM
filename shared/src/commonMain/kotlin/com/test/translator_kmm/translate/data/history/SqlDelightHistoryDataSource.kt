package com.test.translator_kmm.translate.data.history

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.test.translator_kmm.core.domain.util.CommonFlow
import com.test.translator_kmm.core.domain.util.toCommonFlow
import com.test.translator_kmm.database.TranslateDatabase
import com.test.translator_kmm.translate.domain.history.HistoryDataSource
import com.test.translator_kmm.translate.domain.history.HistoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightHistoryDataSource(
    db: TranslateDatabase
) : HistoryDataSource {

    private val queries = db.translateQueries

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return queries.getHistory().asFlow().mapToList(Dispatchers.Default).map { history ->
                history.map { it.toHistoryItem() }
            }.toCommonFlow()
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