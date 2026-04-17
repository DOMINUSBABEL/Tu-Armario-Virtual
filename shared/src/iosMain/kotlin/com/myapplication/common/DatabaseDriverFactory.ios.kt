package com.myapplication.common

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.myapplication.db.ArmarioDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(ArmarioDatabase.Schema, "armario.db")
    }
}
