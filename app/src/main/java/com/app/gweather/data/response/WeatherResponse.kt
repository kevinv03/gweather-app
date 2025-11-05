package com.app.gweather.data.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    val coord: Coord,
    val weather: List<WeatherItem>,
    val main: Main,
    val sys: Sys,
    val name: String,
    val dt: Long
)


@JsonClass(generateAdapter = true)
data class Coord(val lon: Double, val lat: Double)
@JsonClass(generateAdapter = true)
data class WeatherItem(val id: Int, val main: String, val description: String, val icon: String)
@JsonClass(generateAdapter = true)
data class Main(val temp: Double)
@JsonClass(generateAdapter = true)
data class Sys(val country: String, val sunrise: Long, val sunset: Long)