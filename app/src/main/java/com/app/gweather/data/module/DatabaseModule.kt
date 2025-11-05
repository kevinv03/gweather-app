package com.app.gweather.data.module

import android.content.Context
import androidx.room.Room
import com.app.gweather.data.AppDatabase
import com.app.gweather.data.dao.WeatherHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "weather_db")
            .addMigrations(AppDatabase.MIGRATION_1_2) // if you have migrations
            .build()
    }

    @Provides
    fun provideWeatherHistoryDao(db: AppDatabase): WeatherHistoryDao = db.weatherHistoryDao()
}
