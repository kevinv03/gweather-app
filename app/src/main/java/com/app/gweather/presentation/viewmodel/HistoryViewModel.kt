package com.app.gweather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.app.gweather.domain.models.Weather
import com.app.gweather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.collectLatest
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repo: WeatherRepository
) : ViewModel() {

    private val _history = MutableStateFlow<List<Weather>>(emptyList())
    val history: StateFlow<List<Weather>> = _history.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        observeHistory()
    }

    private fun observeHistory() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // prefer Flow API for live updates
                repo.getHistoryFlow().collectLatest { list ->
                    _history.value = list
                    _isLoading.value = false
                }
            } catch (t: Throwable) {
                _history.value = emptyList()
                _isLoading.value = false
            }
        }
    }

    // Keep if you want an explicit reload
    fun reload() {
        // Room Flow is live so reload isn't strictly necessary, but keeps API parity.
    }
}

