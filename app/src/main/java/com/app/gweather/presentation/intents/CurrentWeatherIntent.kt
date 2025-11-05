package com.app.gweather.presentation.intents

sealed class CurrentWeatherIntent {
    object LoadInitial : CurrentWeatherIntent()
    object Refresh : CurrentWeatherIntent()
}