package br.com.seucaio.cryptoexchanges.data.error

import org.junit.Assert.assertEquals
import org.junit.Test

class DataUpdateFailedWarningTest {

    @Test
    fun `NetworkError should hold the correct message`() {
        val message = "No internet connection"
        val error = DataUpdateFailedWarning.NetworkError(message)
        assertEquals(message, error.message)
    }

    @Test
    fun `ApiError should hold the correct message`() {
        val message = "API returned an error"
        val error = DataUpdateFailedWarning.ApiError(message)
        assertEquals(message, error.message)
    }

    @Test
    fun `UnknownError should hold the correct message`() {
        val message = "An unknown error occurred"
        val error = DataUpdateFailedWarning.UnknownError(message)
        assertEquals(message, error.message)
    }
}