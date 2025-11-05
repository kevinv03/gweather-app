package com.app.gweather.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class LocationProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val client: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getLastKnownLocation(): Location = suspendCancellableCoroutine { cont ->
        val fineGranted = androidx.core.content.ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED

        val coarseGranted = androidx.core.content.ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED

        if (!fineGranted && !coarseGranted) {
            cont.resumeWithException(SecurityException("Location permission not granted"))
            return@suspendCancellableCoroutine
        }

        client.lastLocation
            .addOnSuccessListener { loc ->
                if (loc != null) cont.resume(loc)
                else {
                    cont.resumeWithException(IllegalStateException("No last known location"))
                }
            }
            .addOnFailureListener { e -> cont.resumeWithException(e) }

        cont.invokeOnCancellation {
            // cleanup if needed
        }
    }
}
