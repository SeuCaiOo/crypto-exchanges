package br.com.seucaio.cryptoexchanges.domain.usecase

import br.com.seucaio.cryptoexchanges.domain.model.Exchange
import br.com.seucaio.cryptoexchanges.domain.repository.ExchangeRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetExchangesUseCaseTest {
    private val repository: ExchangeRepository = mockk()
    private val getExchangesUseCase = GetExchangesUseCase(repository)

    @Test
    fun `should call getExchanges from repository when forceRefresh is false`() = runTest {
        // Given
        val expectedExchanges = listOf(mockk<Exchange>(), mockk<Exchange>())
        coEvery { repository.getExchanges() } returns flowOf(expectedExchanges)

        // When
        val result = getExchangesUseCase.invoke(false).first()

        // Then
        assertEquals(expectedExchanges, result)
        coVerify(exactly = 1) { repository.getExchanges() }
        coVerify(exactly = 0) { repository.refreshExchanges() }
    }

    @Test
    fun `should call refreshExchanges from repository when forceRefresh is true`() = runTest {
        // Given
        val expectedExchanges = listOf(mockk<Exchange>())
        coEvery { repository.refreshExchanges() } returns flowOf(expectedExchanges)

        // When
        getExchangesUseCase.invoke(true).first()

        // Then
        coVerify(exactly = 1) { repository.refreshExchanges() }
        coVerify(exactly = 0) { repository.getExchanges() }
    }

    @Test
    fun `should propagate exceptions from getExchanges when forceRefresh is false`() = runTest {
        // Given
        val expectedMessage = "Network error"
        coEvery { repository.getExchanges() } throws Exception(expectedMessage)

        // When
        val exception = assertFailsWith<Exception> {
            getExchangesUseCase.invoke(false).first()
        }

        // Then
        assertEquals(expectedMessage, exception.message)

    }

    @Test
    fun `should propagate exceptions from refreshExchanges when forceRefresh is true`() = runTest {
        // Given
        val expectedMessage = "Network error"
        coEvery { repository.refreshExchanges() } throws Exception(expectedMessage)

        // When
        val exception = assertFailsWith<Exception> {
            getExchangesUseCase.invoke(true).first()
        }

        // Then
        assertEquals(expectedMessage, exception.message)
    }
}
