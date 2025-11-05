package com.app.gweather.data.module

import com.app.gweather.data.dao.WeatherHistoryDao
import com.app.gweather.data.remote.OpenWeatherService
import com.app.gweather.data.repository.WeatherRepositoryImpl
import com.app.gweather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: OpenWeatherService,
        dao: WeatherHistoryDao
    ): WeatherRepository {
        return WeatherRepositoryImpl(api, dao)
    }
}