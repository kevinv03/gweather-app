package com.app.gweather.data.remote

import com.app.gweather.data.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.app.gweather.BuildConfig

interface OpenWeatherService {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric", // Celsius
        @Query("appid") apiKey: String = BuildConfig.OPENWEATHER_API_KEY
    ): WeatherResponse
}