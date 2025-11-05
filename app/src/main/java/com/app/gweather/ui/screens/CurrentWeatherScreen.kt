package com.app.gweather.ui.screens

import com.app.gweather.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.gweather.domain.models.Weather
import com.app.gweather.presentation.intents.CurrentWeatherIntent
import com.app.gweather.presentation.state.CurrentWeatherState
import com.app.gweather.presentation.viewmodel.CurrentWeatherViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun CurrentWeatherScreen(vm: CurrentWeatherViewModel = hiltViewModel()) {
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) {
        vm.process(CurrentWeatherIntent.LoadInitial)
    }

    CurrentWeatherContent(state = state)
}

@Composable
fun CurrentWeatherContent(state: CurrentWeatherState) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            return@Box
        }

        state.weather?.let { w ->
            Column(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val iconRes = selectWeatherIcon(w)
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = "weather icon",
                    modifier = Modifier.size(96.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text("${w.city}, ${w.country}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("${w.tempC}°C", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Text("Sunrise: ${epochToLocalTime(w.sunriseEpoch)}")
                Text("Sunset: ${epochToLocalTime(w.sunsetEpoch)}")
            }
        } ?: run {
            val msg = state.error ?: "No data. Grant location or retry."
            Text(msg, modifier = Modifier.align(Alignment.Center))
        }
    }
}

fun selectWeatherIcon(w: Weather): Int {
    val now = Instant.now()
    val nowEpoch = now.epochSecond
    // is it night? use sunrise/sunset from weather entry
    val isNight = nowEpoch < w.sunriseEpoch || nowEpoch >= w.sunsetEpoch || now.atZone(ZoneId.systemDefault()).hour >= 18

    val cond = w.condition.lowercase()
    return when {
        cond.contains("rain") || cond.contains("drizzle") || cond.contains("thunder") -> R.mipmap.ic_launcher_round
        cond.contains("snow") -> R.mipmap.ic_launcher_round
        cond.contains("cloud") || cond.contains("overcast") -> R.mipmap.ic_launcher_round
        else -> if (isNight) R.mipmap.ic_launcher_round else R.mipmap.ic_launcher_round
    }
}


fun epochToLocalTime(epochSec: Long): String {
    return try {
        val z = Instant.ofEpochSecond(epochSec).atZone(ZoneId.systemDefault())
        z.format(DateTimeFormatter.ofPattern("hh:mm a"))
    } catch (t: Throwable) {
        "-"
    }
}
