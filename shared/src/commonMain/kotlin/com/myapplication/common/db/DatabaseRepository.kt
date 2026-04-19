package com.myapplication.common.db

import com.myapplication.common.DatabaseDriverFactory
import com.myapplication.common.createDatabase
import com.myapplication.db.ArmarioDatabase
import com.myapplication.db.WardrobeItem

object DatabaseRepository {
    private var database: ArmarioDatabase? = null

    fun init(driverFactory: DatabaseDriverFactory) {
        if (database == null) {
            database = createDatabase(driverFactory)
        }
    }

    private val queries get() = database!!.armarioDatabaseQueries

    fun insertWardrobeItem(category: String, color: String, imageBytes: ByteArray) {
        queries.insertWardrobeItem(category, color, imageBytes)
    }

    fun getAllWardrobeItems(): List<WardrobeItem> {
        return queries.selectAllWardrobeItems().executeAsList()
    }
}