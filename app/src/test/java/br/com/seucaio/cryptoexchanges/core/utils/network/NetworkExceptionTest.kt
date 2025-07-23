
package br.com.seucaio.cryptoexchanges.core.utils.network

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class NetworkExceptionTest {

    @Test
    fun `ParseException should have default message`() {
        //
        val expectedMessage = "Erro ao ler a resposta da API."

        // Given
        val exception = NetworkException.ParseException()

        // Then
        assertNotNull(exception.message)
        assertEquals(expected = expectedMessage, actual = exception.message)
    }

    @Test
    fun `NoInternetException should have default message`() {
        // Given
        val expectedMessage = "Sem conexão com a internet. Verifique sua rede e tente novamente."

        // When
        val exception = NetworkException.NoInternetException()

        // Then
        assertNotNull(exception.message)
        assertEquals(expected = expectedMessage, actual = exception.message)

    }

    @Test
    fun `NetworkTimeout should have default message`() {
        // Given
        val expectedMessage = "Tempo limite excedido - Verifique sua conexão."

        // When
        val exception = NetworkException.NetworkTimeout()

        // Then
        assertNotNull(exception.message)
        assertEquals(expected = expectedMessage, actual = exception.message)
    }

    @Test
    fun `ConnectionException should have default message`() {
        // Given
        val expectedMessage = "Erro de conexão. Verifique sua internet e tente novamente."

        // When
        val exception = NetworkException.ConnectionException()

        // Then
        assertNotNull(exception.message)
        assertEquals(expected = expectedMessage, actual = exception.message)
    }

    @Test
    fun `ApiException should haven't default message`() {
        // When
        val exception = NetworkException.ApiException()

        // Then
        assertNull(exception.message)
    }

    @Test
    fun `ApiException should have custom message`() {
        // Given
        val expectedMessage = "Custom API error"

        // When
        val exception = NetworkException.ApiException(expectedMessage)

        // Then
        assertNotNull(exception.message)
        assertEquals(expected = expectedMessage, actual = exception.message)
    }

    @Test
    fun `UnknownException should haven't default message`() {
        // When
        val exception = NetworkException.UnknownException()

        // Then
        assertNull(exception.message)
    }

    @Test
    fun `UnknownException should have custom message`() {
        // Given
        val expectedMessage = "Custom unknown"

        // When
        val exception = NetworkException.UnknownException(expectedMessage)

        // Then
        assertNotNull(exception.message)
        assertEquals(expected = expectedMessage, actual = exception.message)
    }
}
