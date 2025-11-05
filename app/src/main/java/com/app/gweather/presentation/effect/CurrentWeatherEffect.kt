package com.app.gweather.presentation.effect

sealed class CurrentWeatherEffect {
    data class ShowToast(val message: String) : CurrentWeatherEffect()
}