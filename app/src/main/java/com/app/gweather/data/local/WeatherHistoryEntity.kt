package com.app.gweather.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_history")
data class WeatherHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val city: String,
    val country: String,
    val tempC: Double,
    val timestamp: Long,
    val sunriseEpoch: Long = 0L,
    val sunsetEpoch: Long = 0L
)
