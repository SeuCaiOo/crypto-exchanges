package br.com.seucaio.cryptoexchanges.core.utils.network

sealed class NetworkException : Exception() {
    data class NoInternetException(
        override val message: String = "Sem conexão com a internet. Verifique sua rede e tente novamente."
    ) : NetworkException()

    data class NetworkTimeout(
        override val message: String = "Tempo limite excedido - Verifique sua conexão"
    ) : NetworkException()

    data class ApiException(
        override val message: String? = "Erro ao processar os dados recebidos da API.",
        val code: Int
    ) : NetworkException()

    data class ConnectionException(
        override val message: String = "Erro de conexão. Verifique sua internet e tente novamente."
    ) : NetworkException()

    data class ParseException(
        override val message: String = "Erro ao ler a resposta da API."
    ) : NetworkException()

    data class UnknownException(
        override val message: String? = "Erro desconhecido."
    ) : NetworkException()
}