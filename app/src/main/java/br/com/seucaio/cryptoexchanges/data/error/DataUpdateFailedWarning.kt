package br.com.seucaio.cryptoexchanges.data.error

sealed class DataUpdateFailedWarning : Throwable() {
    data class NetworkError(override val message: String?) : DataUpdateFailedWarning()
    data class ApiError(override val message: String?) : DataUpdateFailedWarning()
    data class UnknownError(override val message: String?) : DataUpdateFailedWarning()
}