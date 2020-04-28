package com.example.notepad.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.test.platform.app.InstrumentationRegistry


object Utils {
    enum class NetworkType(val type: String) {
        WIFI("wifi"),
        CELLULAR("data")
    }

    fun setNetwork(networkType: NetworkType, enable: Boolean) {
        val state: String = if (enable) "enable" else "disable"
        val type: String = networkType.type

        executeShellCommand("svc $type $state")
    }

    fun isNetworkEnabled(networkType: NetworkType, context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false

        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork)!!

        when (networkType) {
            NetworkType.WIFI -> {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                    return true
            }
            NetworkType.CELLULAR -> {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                    return true
            }
        }
        return false
    }

    fun executeShellCommand(command: String) {
        InstrumentationRegistry
            .getInstrumentation()
            .uiAutomation
            .executeShellCommand(command)
    }
}