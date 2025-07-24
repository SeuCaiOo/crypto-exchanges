
package br.com.seucaio.cryptoexchanges.core.utils.network

import io.mockk.mockk
import kotlinx.serialization.SerializationException
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NetworkExceptionTest {

    @Test
    fun `should return default message when ParseException is created`() {
        // Given
        val expectedMessage = "Erro ao processar os dados recebidos."

        // When
        val exception = NetworkException.ParseException()

        // Then
        assertEquals(expected = expectedMessage, actual = exception.message)
    }

    @Test
    fun `should return default message when NoInternetException is created`() {
        // Given
        val expectedMessage = " Sem conexão com a internet, verifique sua rede."

        // When
        val exception = NetworkException.NoInternetException()

        // Then
        assertEquals(expected = expectedMessage, actual = exception.message)
    }

    @Test
    fun `should return default message when NetworkTimeout is created`() {
        // Given
        val expectedMessage = "A requisição demorou muito para responder, verifique sua internet."

        // When
        val exception = NetworkException.NetworkTimeout()

        // Then
        assertEquals(expected = expectedMessage, actual = exception.message)
    }

    @Test
    fun `should return default message when ConnectionException is created`() {
        // Given
        val expectedMessage = "Não foi possível conectar ao servidor, verifique sua internet."

        // When
        val exception = NetworkException.ConnectionException()

        // Then
        assertEquals(expected = expectedMessage, actual = exception.message)
    }

    @Test
    fun `should return default message for null httpStatusCode when ApiException is created`() {
        // Given
        val expectedMessage = "Erro na comunicação com a API."

        // When
        val exception = NetworkException.ApiException()

        // Then
        assertEquals(expected = expectedMessage, actual = exception.message)
    }

    @Test
    fun `should return specific message for 401 httpStatusCode when ApiException is created`() {
        // Given
        val httpStatusCode = 401
        val expectedMessage = "Não autorizado, verifique sua API key (HTTP 401)."

        // When
        val exception = NetworkException.ApiException(httpStatusCode = httpStatusCode)

        // Then
        assertEquals(expected = expectedMessage, actual = exception.message)
    }

    @Test
    fun `should return specific message for 429 httpStatusCode when ApiException is created`() {
        // Given
        val httpStatusCode = 429
        val expectedMessage = "Muitas requisições, tente novamente mais tarde (HTTP 429)."

        // When
        val exception = NetworkException.ApiException(httpStatusCode = httpStatusCode)

        // Then
        assertEquals(expected = expectedMessage, actual = exception.message)
    }

    @Test
    fun `should return specific message for 500 httpStatusCode when ApiException is created`() {
        // Given
        val httpStatusCode = 500
        val expectedMessage = "Erro no servidor, tente novamente mais tarde (HTTP 500)."

        // When
        val exception = NetworkException.ApiException(httpStatusCode = httpStatusCode)

        // Then
        assertEquals(expected = expectedMessage, actual = exception.message)
    }

    @Test
    fun `should return default message when UnknownException is created without message`() {
        // Given
        val expectedMessage = "Ocorreu um erro desconhecido."

        // When
        val exception = NetworkException.UnknownException()

        // Then
        assertEquals(expected = expectedMessage, actual = exception.message)
    }

    @Test
    fun `should return custom message when UnknownException is created with message`() {
        // Given
        val expectedMessage = "Custom unknown error"

        // When
        val exception = NetworkException.UnknownException(expectedMessage)

        // Then
        assertEquals(expected = expectedMessage, actual = exception.message)
    }

    @Test
    fun `should return ParseException when fromThrowable receives SerializationException`() {
        // Given
        val throwable = SerializationException()

        // When
        val result = NetworkException.fromThrowable(throwable)

        // Then
        assertTrue(result is NetworkException.ParseException)
        assertEquals(throwable, result.cause)
    }

    @Test
    fun `should return NetworkTimeout when fromThrowable receives SocketTimeoutException`() {
        // Given
        val throwable = SocketTimeoutException()

        // When
        val result = NetworkException.fromThrowable(throwable)

        // Then
        assertTrue(result is NetworkException.NetworkTimeout)
        assertEquals(throwable, result.cause)
    }

    @Test
    fun `should return ConnectionException when fromThrowable receives ConnectException`() {
        // Given
        val throwable = ConnectException()

        // When
        val result = NetworkException.fromThrowable(throwable)

        // Then
        assertTrue(result is NetworkException.ConnectionException)
        assertEquals(throwable, result.cause)
    }

    @Test
    fun `should return ApiException when fromThrowable receives HttpException`() {
        // Given
        val httpStatusCode = 404
        val throwable = HttpException(Response.error<Any>(httpStatusCode, mockk(relaxed = true)))

        // When
        val result = NetworkException.fromThrowable(throwable)

        // Then
        assertTrue(result is NetworkException.ApiException)
        assertEquals(httpStatusCode, result.httpStatusCode)
        assertEquals(throwable, result.cause)
    }

    @Test
    fun `should return UnknownException when fromThrowable receives a generic Exception`() {
        // Given
        val errorMessage = "Generic error"
        val throwable = Exception(errorMessage)

        // When
        val result = NetworkException.fromThrowable(throwable)

        // Then
        assertTrue(result is NetworkException.UnknownException)
        assertEquals(errorMessage, result.message)
        assertEquals(throwable, result.cause)
    }
}
