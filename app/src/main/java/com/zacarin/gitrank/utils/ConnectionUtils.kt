package com.zacarin.gitrank.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import org.koin.core.KoinComponent
import org.koin.core.inject

object ConnectionUtils : KoinComponent {

    private val tag = this::class.java.simpleName
    private val appContext : Context by inject()

    fun isInternetAccessAvailable() : Boolean {
        var isInternetConnectivityAvailable = false

        val connectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

        connectivityManager?.let { manager ->
            val capabilities =
                manager.getNetworkCapabilities(connectivityManager.activeNetwork)

            capabilities?.also { capabilities ->
                isInternetConnectivityAvailable = when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Log.d(tag, "Cellular connection available\"")
                        true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Log.i(tag, "Wi-Fi connection available\"")
                        true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Log.i(tag, "Ethernet connection available")
                        true
                    }
                    else -> false
                }
            }
        }
        return isInternetConnectivityAvailable
    }
}