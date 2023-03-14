package com.paras.kavach.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log

/**
 * Check Internet (MOBILE/WIFI/ETHERNET)
 */
@Suppress("DEPRECATION")
fun Context.isOnline(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities: NetworkCapabilities
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)!!
    } else {
        return connectivityManager.activeNetworkInfo?.isConnected!!
    }
    when {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
            return true
        }
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
            return true
        }
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
            return true
        }
    }
    "Check Internet Connection".shortToast(this)
    return false
}