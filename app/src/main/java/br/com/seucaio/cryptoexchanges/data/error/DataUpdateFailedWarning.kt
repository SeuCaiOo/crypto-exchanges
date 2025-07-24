package br.com.seucaio.cryptoexchanges.data.error

import br.com.seucaio.cryptoexchanges.core.utils.network.NetworkException

sealed class DataUpdateFailedWarning(
    override val message: String? = null
) : Exception(message) {
    class NetworkError(message: String) : DataUpdateFailedWarning(message)
    class ApiError(message: String) : DataUpdateFailedWarning(message)
    class UnknownError(message: String) : DataUpdateFailedWarning(message)

    companion object {
        fun fromThrowable(e: Throwable): DataUpdateFailedWarning {
            val message = "Falha na atualização dos dados.\n${e.message}"
            return when (e) {
                is NetworkException.NoInternetException -> NetworkError(message)
                is NetworkException.ApiException -> ApiError(message)
                else -> UnknownError(message)
            }
        }
    }
}