# 🌦️ GWeather — Android Weather App

GWeather is a modern Android weather application built with **Kotlin**, **Jetpack Compose**, **Hilt (Dagger)**, **Room**, and **Retrofit**.  
It fetches real-time weather data from the **OpenWeather API** and stores user search history locally for quick access.

---

## 🧩 Features

- 🌤️ **Current Weather**: Displays up-to-date weather information based on coordinates (latitude/longitude).
- 📜 **Weather History**: Saves and retrieves previous searches using Room database.
- ⚡ **Dependency Injection**: Powered by Hilt for clean architecture and testability.
- 🌐 **Remote API Integration**: Uses Retrofit to call OpenWeather API.
- 🗺️ **Location Support**: Fetches weather automatically based on device location.
- 💾 **Offline Storage**: Local caching of weather history data.

---

## 🏗️ Tech Stack

| Layer | Technology |
|-------|-------------|
| UI | Jetpack Compose, Material3 |
| DI | Hilt (Dagger) |
| Data | Room Database, Repository Pattern |
| Network | Retrofit, Coroutines |
| Architecture | MVVM (Model–View–ViewModel) |
| Build System | Gradle (KTS) |
