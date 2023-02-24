package com.test.translator_kmm.translate.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.test.translator_kmm.database.TranslateDatabase

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver = NativeSqliteDriver(TranslateDatabase.Schema, "translate.db")
}