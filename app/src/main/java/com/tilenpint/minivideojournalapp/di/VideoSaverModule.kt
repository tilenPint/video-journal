package com.tilenpint.minivideojournalapp.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.tilenpint.minivideojournalapp.Database
import com.tilenpint.minivideojournalapp.VideoQueries
import org.koin.dsl.module

val videoSaverModule = module {
    single<VideoQueries> {
        val driver: SqlDriver = AndroidSqliteDriver(Database.Schema, get(), "Video.db")

        val database = Database(driver)
        database.videoQueries
    }
}