package com.app.gweather.domain.models

data class Weather(
    val city: String,
    val country: String,
    val tempC: Double,
    val sunriseEpoch: Long,
    val sunsetEpoch: Long,
    val condition: String,
    val timestamp: Long
)