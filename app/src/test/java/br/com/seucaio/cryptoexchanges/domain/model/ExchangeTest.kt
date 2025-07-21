package br.com.seucaio.cryptoexchanges.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class ExchangeTest {

    @Test
    fun `Exchange data class should have correct properties`() {
        val exchange = Exchange(
            exchangeId = "binance",
            name = "Binance",
            website = "https://www.binance.com",
            icons = listOf(Exchange.Icon(url = "https://assets.coingecko.com/markets/images/1/small/binance.png?1520360896")),
            dataStart = "2017"
        )

        assertEquals("binance", exchange.exchangeId)
        assertEquals("Binance", exchange.name)
        assertEquals("https://www.binance.com", exchange.website)
        assertEquals("https://assets.coingecko.com/markets/images/1/small/binance.png?1520360896", exchange.icons?.firstOrNull()?.url)
        assertEquals("2017", exchange.dataStart)
    }

    @Test
    fun `Exchange data class should handle null values`() {
        val exchange = Exchange(
            exchangeId = "kraken",
            name = "Kraken",
            website = null,
            icons = null,
            dataStart = null
        )

        assertEquals("kraken", exchange.exchangeId)
        assertEquals("Kraken", exchange.name)
        assertEquals(null, exchange.website)
        assertEquals(null, exchange.icons)
        assertEquals(null, exchange.dataStart)
    }

    @Test
    fun `Exchange data class should have correct equality`() {
        val exchange1 = Exchange("binance", "https://www.binance.com", "Binance", "2017", icons = listOf(Exchange.Icon(url = "url1")))
        val exchange2 = Exchange("binance", "https://www.binance.com", "Binance", "2017", icons = listOf(Exchange.Icon(url = "url1")))
        val exchange3 = Exchange("kraken", "https://www.kraken.com", "Kraken", "2011", icons = listOf(Exchange.Icon(url = "url2")))

        assertEquals(exchange1, exchange2)
        assertNotEquals(exchange1, exchange3)
    }

    @Test
    fun `Exchange data class should have correct hash code`() {
        val exchange1 = Exchange("binance", "https://www.binance.com", "Binance", "2017", icons = listOf(Exchange.Icon(url = "url1")))
        val exchange2 = Exchange("binance", "https://www.binance.com", "Binance", "2017", icons = listOf(Exchange.Icon(url = "url1")))

        assertEquals(exchange1.hashCode(), exchange2.hashCode())
    }

    @Test
    fun `Exchange data class should have correct copy behavior`() {
        val original = Exchange("binance", "https://www.binance.com", "Binance", "2017", icons = listOf(Exchange.Icon(url = "url1")))
        val copied = original.copy(name = "Binance US")

        assertEquals("binance", copied.exchangeId)
        assertEquals("Binance US", copied.name)
        assertEquals("https://www.binance.com", copied.website)
        assertEquals("url1", copied.icons?.firstOrNull()?.url)
        assertEquals("2017", copied.dataStart)
        assertNotEquals(original, copied)
    }
}