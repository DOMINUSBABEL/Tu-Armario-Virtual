package com.myapplication.common

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DatabaseDriverFactory): com.myapplication.db.ArmarioDatabase {
    val driver = driverFactory.createDriver()
    return com.myapplication.db.ArmarioDatabase(driver)
}
