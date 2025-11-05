package com.app.gweather.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.gweather.domain.models.Weather
import com.app.gweather.presentation.viewmodel.HistoryViewModel
import com.google.crypto.tink.shaded.protobuf.LazyStringArrayList.emptyList
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun HistoryScreen(vm: HistoryViewModel = hiltViewModel()) {
    val history by vm.history.collectAsState(initial = emptyList())
    val isLoading by vm.isLoading.collectAsState()

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        WeatherHistoryList(history as List<Weather>)
    }
}

@Composable
fun WeatherHistoryList(items: List<Weather>) {
    if (items.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text("No history yet. Each app open saves current weather.")
        }
        return
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(items) { w ->
            WeatherHistoryItem(w)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun WeatherHistoryItem(w: Weather) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("${w.city}, ${w.country}", style = MaterialTheme.typography.titleLarge)
            Text("${w.tempC}°C", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(6.dp))
            Row {
                Text("Sunrise: ${formatEpoch(w.sunriseEpoch)}", modifier = Modifier.weight(1f))
                Text("Sunset: ${formatEpoch(w.sunsetEpoch)}", modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text("Fetched: ${formatEpoch(w.timestamp)}", style = MaterialTheme.typography.titleSmall)
        }
    }
}


fun formatEpoch(epochSec: Long): String {
    return try {
        val z = Instant.ofEpochSecond(epochSec).atZone(ZoneId.systemDefault())
        z.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"))
    } catch (t: Throwable) {
        "-"
    }
}
