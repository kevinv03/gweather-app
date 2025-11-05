package com.app.gweather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.gweather.data.dao.WeatherHistoryDao
import com.app.gweather.data.local.WeatherHistoryEntity

@Database(entities = [WeatherHistoryEntity::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherHistoryDao(): WeatherHistoryDao


    companion object {
        // Migration from version 1 -> 2: add sunriseEpoch and sunsetEpoch with default 0
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
// Add new columns with default values so existing rows receive 0
                database.execSQL("ALTER TABLE weather_history ADD COLUMN sunriseEpoch INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE weather_history ADD COLUMN sunsetEpoch INTEGER NOT NULL DEFAULT 0")
            }
        }


// If you later add MIGRATION_2_3 etc, register them when building the DB.
    }
}