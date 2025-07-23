package br.com.seucaio.cryptoexchanges.core.utils.network

sealed class NetworkException : Exception() {
    data class ParseException(
        override val message: String = "Erro ao ler a resposta da API."
    ) : NetworkException()

    data class NetworkTimeout(
        override val message: String = "Tempo limite excedido - Verifique sua conexão."
    ) : NetworkException()

    data class ConnectionException(
        override val message: String = "Erro de conexão. Verifique sua internet e tente novamente."
    ) : NetworkException()

    data class NoInternetException(
        override val message: String = "Sem conexão com a internet. Verifique sua rede e tente novamente."
    ) : NetworkException()

    data class ApiException(
        override val message: String? = null,
    ) : NetworkException()

    data class UnknownException(
        override val message: String? = null
    ) : NetworkException()
}