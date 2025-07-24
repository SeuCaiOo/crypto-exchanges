package br.com.seucaio.cryptoexchanges.core.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConnectivityChecker(context: Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isNetworkAvailable(): Boolean {
        val currentActiveNetwork = connectivityManager.activeNetwork ?: return false
        val currentNetworkCapabilities =
            connectivityManager.getNetworkCapabilities(currentActiveNetwork) ?: return false

        val transportTypes = listOf(
            NetworkCapabilities.TRANSPORT_WIFI,
            NetworkCapabilities.TRANSPORT_CELLULAR,
            NetworkCapabilities.TRANSPORT_ETHERNET,
            NetworkCapabilities.TRANSPORT_VPN
        )

        return transportTypes.any { currentNetworkCapabilities.hasTransport(it) }
    }
}

suspend fun <T> ConnectivityChecker.executeNetworkRequest(action: suspend () -> T) =
    withContext(Dispatchers.IO) {
        if (!isNetworkAvailable()) throw NetworkException.NoInternetException()
        try {
            action.invoke()
        } catch (e: Exception) {
            throw NetworkException.fromThrowable(e)
        }
    }