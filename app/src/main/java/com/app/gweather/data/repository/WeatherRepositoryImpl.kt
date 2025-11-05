package com.app.gweather.data.repository

import com.app.gweather.data.dao.WeatherHistoryDao
import com.app.gweather.data.local.WeatherHistoryEntity
import com.app.gweather.data.remote.OpenWeatherService
import com.app.gweather.domain.models.Weather
import com.app.gweather.domain.repository.WeatherRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeatherRepositoryImpl @Inject constructor(
    private val api: OpenWeatherService,
    private val dao: WeatherHistoryDao
) : WeatherRepository {

    override suspend fun fetchCurrentWeather(lat: Double, lon: Double): Weather {
        val resp = api.getCurrentWeather(lat, lon)
        return Weather(
            city = resp.name,
            country = resp.sys.country,
            tempC = resp.main.temp,
            sunriseEpoch = resp.sys.sunrise,
            sunsetEpoch = resp.sys.sunset,
            condition = resp.weather.firstOrNull()?.main ?: "",
            timestamp = resp.dt
        )
    }

    override suspend fun saveHistory(weather: Weather) {
        dao.insert(
            WeatherHistoryEntity(
                city = weather.city,
                country = weather.country,
                tempC = weather.tempC,
                timestamp = weather.timestamp,
                sunriseEpoch = weather.sunriseEpoch,
                sunsetEpoch = weather.sunsetEpoch
            )
        )
    }

    override suspend fun getHistory(): List<Weather> {
        return dao.getAll().map { entity ->
            Weather(
                city = entity.city,
                country = entity.country,
                tempC = entity.tempC,
                sunriseEpoch = entity.sunriseEpoch,
                sunsetEpoch = entity.sunsetEpoch,
                condition = "",
                timestamp = entity.timestamp
            )
        }
    }

    // new: a Flow-based API so UI can react to DB changes
    override fun getHistoryFlow(): Flow<List<Weather>> {
        return dao.getAllFlow().map { list ->
            list.map { entity ->
                Weather(
                    city = entity.city,
                    country = entity.country,
                    tempC = entity.tempC,
                    sunriseEpoch = entity.sunriseEpoch,
                    sunsetEpoch = entity.sunsetEpoch,
                    condition = "",
                    timestamp = entity.timestamp
                )
            }
        }
    }
}