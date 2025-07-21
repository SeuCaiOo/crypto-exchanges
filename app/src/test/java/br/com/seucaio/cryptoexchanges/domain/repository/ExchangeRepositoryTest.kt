package br.com.seucaio.cryptoexchanges.domain.repository

import br.com.seucaio.cryptoexchanges.domain.model.Exchange
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExchangeRepositoryTest {

    private lateinit var repository: ExchangeRepository

    @Before
    fun setup() {
        repository = mockk()
    }

    @Test
    fun `getExchanges should return a flow of exchanges`() = runBlocking {
        // Given
        val expectedExchanges = listOf(
            Exchange(
                exchangeId = "BINANCE",
                name = "Binance",
                website = "https://www.binance.com",
                volume1HrsUsd = 1000.0,
                volume1DayUsd = 2000.0,
                volume1MthUsd = 3000.0,
                icons = listOf(Exchange.Icon(url = "https://assets.coincap.io/assets/icons/binance@2x.png"))
            ),
            Exchange(
                exchangeId = "COINBASE",
                name = "Coinbase",
                website = "https://www.coinbase.com",
                volume1HrsUsd = 4000.0,
                volume1DayUsd = 5000.0,
                volume1MthUsd = 6000.0,
                icons = listOf(Exchange.Icon(url = "https://assets.coincap.io/assets/icons/coinbase@2x.png"))
            )
        )
        every { repository.getExchanges(any()) } returns flowOf(expectedExchanges)

        // When
        val result: Flow<List<Exchange>> = repository.getExchanges(false)

        // Then
        result.collect { exchanges ->
            assertEquals(expectedExchanges, exchanges)
        }
    }
}