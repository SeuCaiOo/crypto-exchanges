package br.com.seucaio.cryptoexchanges.core.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

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
            action()
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> throw NetworkException.NetworkTimeout()
                is ConnectException -> throw NetworkException.ConnectionException()
                is SerializationException -> throw NetworkException.ParseException()
                is HttpException -> {
                    val httpCode = e.code()
                    val customMessage = when (e.code()) {
                        401 -> "Não autorizado - Verifique sua API key"
                        403 -> "Acesso negado"
                        404 -> "Endpoint não encontrado"
                        429 -> "Muitas requisições - Tente novamente mais tarde"
                        in 500..599 -> "Erro no servidor - Tente novamente mais tarde"
                        else -> "Erro na API: ${e.message}"
                    }
                    throw NetworkException.ApiException(message = "$customMessage (HTTP $httpCode)")
                }

                else -> throw NetworkException.UnknownException(e.message)
            }
        }
    }