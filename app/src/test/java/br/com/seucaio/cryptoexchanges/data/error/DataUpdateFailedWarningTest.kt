package br.com.seucaio.cryptoexchanges.data.error

import br.com.seucaio.cryptoexchanges.core.utils.network.NetworkException
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DataUpdateFailedWarningTest {

    @Test
    fun `should return NetworkError when fromThrowable receives NoInternetException`() {
        // Given
        val originalException = NetworkException.NoInternetException()
        val expectedMessage = getWarningMessage(originalException)

        // When
        val result = DataUpdateFailedWarning.fromThrowable(originalException)

        // Then
        assertTrue(result is DataUpdateFailedWarning.NetworkError)
        assertEquals(expectedMessage, result.message)
    }

    @Test
    fun `should return ApiError when fromThrowable receives ApiException`() {
        // Given
        val originalException = NetworkException.ApiException(httpStatusCode = 404)
        val expectedMessage = getWarningMessage(originalException)

        // When
        val result = DataUpdateFailedWarning.fromThrowable(originalException)

        // Then
        assertTrue(result is DataUpdateFailedWarning.ApiError)
        assertEquals(expectedMessage, result.message)
    }

    @Test
    fun `should return UnknownError when fromThrowable receives a generic Exception`() {
        // Given
        val originalException = Exception("Generic test error")
        val expectedMessage = getWarningMessage(originalException)

        // When
        val result = DataUpdateFailedWarning.fromThrowable(originalException)

        // Then
        assertTrue(result is DataUpdateFailedWarning.UnknownError)
        assertEquals(expectedMessage, result.message)
    }

    @Test
    fun `should return UnknownError when fromThrowable receives a NetworkException-Unknown`() {
        // Given
        val originalException = NetworkException.UnknownException("Unknown network error")
        val expectedMessage = getWarningMessage(originalException)

        // When
        val result = DataUpdateFailedWarning.fromThrowable(originalException)

        // Then
        assertTrue(result is DataUpdateFailedWarning.UnknownError)
        assertEquals(expectedMessage, result.message)
    }

    private fun getWarningMessage(exception: Exception): String {
        return """
                Falha na atualização dos dados.
                ${exception.message}
            """.trimIndent()
    }
}