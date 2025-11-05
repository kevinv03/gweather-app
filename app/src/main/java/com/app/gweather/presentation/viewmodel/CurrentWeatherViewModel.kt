package com.app.gweather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gweather.domain.repository.WeatherRepository
import com.app.gweather.presentation.effect.CurrentWeatherEffect
import com.app.gweather.presentation.intents.CurrentWeatherIntent
import com.app.gweather.presentation.state.CurrentWeatherState
import com.app.gweather.util.LocationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val repo: WeatherRepository,
    private val locationProvider: LocationProvider
) : ViewModel() {


    private val _state = MutableStateFlow(CurrentWeatherState())
    val state: StateFlow<CurrentWeatherState> = _state.asStateFlow()


    private val _effect = MutableSharedFlow<CurrentWeatherEffect>()
    val effect: SharedFlow<CurrentWeatherEffect> = _effect.asSharedFlow()


    // Intent channel
    private val intents = MutableSharedFlow<CurrentWeatherIntent>(extraBufferCapacity = 4)


    init {
// collect intents
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    CurrentWeatherIntent.LoadInitial, CurrentWeatherIntent.Refresh -> loadCurrentWeather()
                }
            }
        }
    }


    fun process(intent: CurrentWeatherIntent) {
        intents.tryEmit(intent)
    }


    private suspend fun loadCurrentWeather() {
        _state.update { it.copy(isLoading = true, error = null) }
        try {
            val loc = locationProvider.getLastKnownLocation()
            val w = repo.fetchCurrentWeather(loc.latitude, loc.longitude)
// save history entry
            repo.saveHistory(w)
            _state.update { CurrentWeatherState(isLoading = false, weather = w, error = null) }
        } catch (t: Throwable) {
            _state.update { CurrentWeatherState(isLoading = false, weather = null, error = t.message ?: "Unknown error") }
            _effect.emit(CurrentWeatherEffect.ShowToast(t.message ?: "Failed to load weather"))
        }
    }
}