package br.com.seucaio.cryptoexchanges.data.remote.dto

import br.com.seucaio.cryptoexchanges.data.mapper.ExchangeMapper
import br.com.seucaio.cryptoexchanges.core.extension.orZero
import org.junit.Assert.assertEquals
import org.junit.Test

class ExchangeResponseTest {

    @Test
    fun `ExchangeResponse should correctly map to Exchange model`() {
        // Given
        val exchangeResponse = ExchangeResponse(
            exchangeId = "BINANCE",
            name = "Binance",
            website = "https://www.binance.com/",
            volume1HrsUsd = 1000.0,
            volume1DayUsd = 2000.0,
            volume1MthUsd = 3000.0,
            icons = listOf(ExchangeResponse.IconResponse(url = "https://assets.coincap.io/exchange_icons/binance.png"))
        )

        // When
        val exchange = ExchangeMapper.mapRemoteToDomain(exchangeResponse)

        // Then
        assertEquals("BINANCE", exchange.exchangeId)
        assertEquals("Binance", exchange.name)
        assertEquals("https://www.binance.com/", exchange.website)
        assertEquals(1000.0, exchange.volume1HrsUsd.orZero(), 0.0)
        assertEquals(2000.0, exchange.volume1DayUsd.orZero(), 0.0)
        assertEquals(3000.0, exchange.volume1MthUsd.orZero(), 0.0)
        assertEquals("https://assets.coincap.io/exchange_icons/binance.png", exchange.icons?.firstOrNull()?.url.orEmpty())
    }

    @Test
    fun `ExchangeResponse with null values should map to Exchange model with default values`() {
        // Given
        val exchangeResponse = ExchangeResponse(
            exchangeId = null,
            name = null,
            website = null,
            volume1HrsUsd = null,
            volume1DayUsd = null,
            volume1MthUsd = null,
            icons = null
        )

        // When
        val exchange = ExchangeMapper.mapRemoteToDomain(exchangeResponse)

        // Then
        assertEquals("", exchange.exchangeId)
        assertEquals("", exchange.name)
        assertEquals("", exchange.website)
        assertEquals(0.0, exchange.volume1HrsUsd.orZero(), 0.0)
        assertEquals(0.0, exchange.volume1DayUsd.orZero(), 0.0)
        assertEquals(0.0, exchange.volume1MthUsd.orZero(), 0.0)
        assertEquals(null, exchange.icons)
    }
}
