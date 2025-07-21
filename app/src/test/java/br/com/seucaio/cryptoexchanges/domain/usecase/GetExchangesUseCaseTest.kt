package br.com.seucaio.cryptoexchanges.domain.usecase

import br.com.seucaio.cryptoexchanges.domain.model.Exchange
import br.com.seucaio.cryptoexchanges.domain.repository.ExchangeRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetExchangesUseCaseTest {

    private val repository: ExchangeRepository = mockk()
    private val getExchangesUseCase = GetExchangesUseCase(repository)

    @Test
    fun `invoke should call getExchanges from repository and return data`() = runTest {
        // Given
        val expectedExchanges = listOf(
            Exchange(exchangeId = "id1", name = "name1", website = "website1", icons = listOf(Exchange.Icon(url = "url1")), dataStart = "2000"),
            Exchange(exchangeId = "id2", name = "name2", website = "website2", icons = listOf(Exchange.Icon(url = "url2")), dataStart = "2001")
        )
        coEvery { repository.getExchanges(any()) } returns flowOf(expectedExchanges)

        // When
        val result = getExchangesUseCase.invoke(false).toList()

        // Then
        coVerify(exactly = 1) { repository.getExchanges(false) }
        assertEquals(expectedExchanges, result[0])
    }

    @Test
    fun `invoke should pass forceRefresh parameter to repository`() = runTest {
        // Given
        val expectedExchanges = listOf(
            Exchange(exchangeId = "id1", name = "name1", website = "website1", icons = listOf(Exchange.Icon(url = "url1")), dataStart = "2000")
        )
        coEvery { repository.getExchanges(true) } returns flowOf(expectedExchanges)

        // When
        getExchangesUseCase.invoke(true).toList()

        // Then
        coVerify(exactly = 1) { repository.getExchanges(true) }
    }

    @Test(expected = Exception::class)
    fun `invoke should propagate exceptions from repository`() = runTest {
        // Given
        coEvery { repository.getExchanges(any()) } throws Exception("Network error")

        // When
        getExchangesUseCase.invoke(false).toList()

        // Then: Exception is expected to be thrown and caught by the test runner
    }
}
