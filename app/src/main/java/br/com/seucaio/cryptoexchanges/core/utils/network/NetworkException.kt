package br.com.seucaio.cryptoexchanges.core.utils.network

import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

sealed class NetworkException(
    override val message: String?,
    override val cause: Throwable? = null
) : Exception(message, cause) {
    class ParseException(
        message: String = "Erro ao processar os dados recebidos.",
        cause: Throwable? = null
    ) : NetworkException(message, cause)

    class NetworkTimeout(
        message: String = "A requisição demorou muito para responder, verifique sua internet.",
        cause: Throwable? = null
    ) : NetworkException(message, cause)

    class ConnectionException(
        message: String = "Não foi possível conectar ao servidor, verifique sua internet.",
        cause: Throwable? = null
    ) : NetworkException(message, cause)

    class NoInternetException(
        message: String = " Sem conexão com a internet, verifique sua rede.",
        cause: Throwable? = null
    ) : NetworkException(message, cause)

    class ApiException(
        val httpStatusCode: Int? = null,
        cause: Throwable? = null
    ) : NetworkException(message = httpStatusCode?.formatApiMessage(), cause = cause)


    class UnknownException(
        message: String? = null,
        cause: Throwable? = null
    ) : NetworkException(message = message ?: "Ocorreu um erro desconhecido.", cause = cause)

    companion object {
        private fun Int?.formatApiMessage(): String {
            if (this == null) return "Erro na comunicação com a API."

            val message = when (this) {
                401 -> "Não autorizado, verifique sua API key"
                403 -> "Acesso negado"
                404 -> "Endpoint não encontrado"
                429 -> "Muitas requisições, tente novamente mais tarde"
                in 500..599 -> "Erro no servidor, tente novamente mais tarde"
                else -> "Erro ao processar os dados recebidos da API"
            }
            return "$message (HTTP $this)."
        }

        fun fromThrowable(e: Throwable): NetworkException {
            return when (e) {
                is SerializationException -> ParseException(cause = e)
                is SocketTimeoutException -> NetworkTimeout(cause = e)
                is ConnectException -> ConnectionException(cause = e)
                is HttpException -> ApiException(httpStatusCode = e.code(), cause = e)
                else -> UnknownException(message = e.message, cause = e)
            }
        }
    }
}