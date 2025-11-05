package com.app.gweather.domain.repository

import com.app.gweather.domain.models.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun fetchCurrentWeather(lat: Double, lon: Double): Weather
    suspend fun saveHistory(weather: Weather)
    suspend fun getHistory(): List<Weather>
    fun getHistoryFlow(): Flow<List<Weather>>
}