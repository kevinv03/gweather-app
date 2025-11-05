package com.app.gweather.presentation.state

import com.app.gweather.domain.models.Weather

data class CurrentWeatherState(
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val error: String? = null
)