package br.com.seucaio.cryptoexchanges.data.mapper

import br.com.seucaio.cryptoexchanges.core.extension.orZero
import br.com.seucaio.cryptoexchanges.data.local.entity.ExchangeEntity
import br.com.seucaio.cryptoexchanges.data.remote.dto.ExchangeResponse
import br.com.seucaio.cryptoexchanges.data.remote.dto.ExchangeResponse.IconResponse
import br.com.seucaio.cryptoexchanges.domain.model.Exchange
import org.junit.Assert.assertEquals
import org.junit.Test

class ExchangeMapperTest {

    @Test
    fun `mapLocalToDomain should correctly map ExchangeEntity to Exchange`() {
        val entity = ExchangeEntity(
            id = "EXCH1",
            website = "website.com",
            name = "Exchange One",
            dataStart = "2023-01-01",
            dataEnd = "2023-01-31",
            dataQuoteStart = "2023-01-01",
            dataQuoteEnd = "2023-01-31",
            dataOrderBookStart = "2023-01-01",
            dataOrderBookEnd = "2023-01-31",
            dataTradeStart = "2023-01-01",
            dataTradeEnd = "2023-01-31",
            dataTradeCount = 100,
            dataSymbolsCount = 10,
            volume1HrsUsd = 1000.0,
            volume1DayUsd = 20000.0,
            volume1MthUsd = 300000.0,
            metricId = listOf("METRIC1"),
            rank = 1,
            integrationStatus = "OK",
            createdAt = System.currentTimeMillis()
        )

        val domain = ExchangeMapper.mapLocalToDomain(entity)

        assertEquals("EXCH1", domain.exchangeId)
        assertEquals("website.com", domain.website)
        assertEquals("Exchange One", domain.name)
        assertEquals("2023-01-01", domain.dataStart)
        assertEquals("2023-01-31", domain.dataEnd)
        assertEquals("2023-01-01", domain.dataQuoteStart)
        assertEquals("2023-01-31", domain.dataQuoteEnd)
        assertEquals("2023-01-01", domain.dataOrderBookStart)
        assertEquals("2023-01-31", domain.dataOrderBookEnd)
        assertEquals("2023-01-01", domain.dataTradeStart)
        assertEquals("2023-01-31", domain.dataTradeEnd)
        assertEquals(100L, domain.dataTradeCount)
        assertEquals(10L, domain.dataSymbolsCount)
        assertEquals(1000.0, domain.volume1HrsUsd.orZero(), 0.0)
        assertEquals(20000.0, domain.volume1DayUsd.orZero(), 0.0)
        assertEquals(300000.0, domain.volume1MthUsd.orZero(), 0.0)
        assertEquals(listOf("METRIC1"), domain.metricId)
        assertEquals(1, domain.rank)
        assertEquals("OK", domain.integrationStatus)
    }

    @Test
    fun `mapRemoteToDomain should correctly map ExchangeResponse to Exchange`() {
        val response = ExchangeResponse(
            exchangeId = "EXCH2",
            website = "website2.com",
            name = "Exchange Two",
            dataStart = "2023-02-01",
            dataEnd = "2023-02-28",
            dataQuoteStart = "2023-02-01",
            dataQuoteEnd = "2023-02-28",
            dataOrderBookStart = "2023-02-01",
            dataOrderBookEnd = "2023-02-28",
            dataTradeStart = "2023-02-01",
            dataTradeEnd = "2023-02-28",
            dataTradeCount = 200,
            dataSymbolsCount = 20,
            volume1HrsUsd = 2000.0,
            volume1DayUsd = 40000.0,
            volume1MthUsd = 600000.0,
            metricId = listOf("METRIC2"),
            rank = 2,
            icons = listOf(IconResponse("ICON2", "ASSET2", "url2.com")),
            integrationStatus = "ACTIVE"
        )

        val domain = ExchangeMapper.mapRemoteToDomain(response)

        assertEquals("EXCH2", domain.exchangeId)
        assertEquals("website2.com", domain.website)
        assertEquals("Exchange Two", domain.name)
        assertEquals("2023-02-01", domain.dataStart)
        assertEquals("2023-02-28", domain.dataEnd)
        assertEquals("2023-02-01", domain.dataQuoteStart)
        assertEquals("2023-02-28", domain.dataQuoteEnd)
        assertEquals("2023-02-01", domain.dataOrderBookStart)
        assertEquals("2023-02-28", domain.dataOrderBookEnd)
        assertEquals("2023-02-01", domain.dataTradeStart)
        assertEquals("2023-02-28", domain.dataTradeEnd)
        assertEquals(200L, domain.dataTradeCount)
        assertEquals(20L, domain.dataSymbolsCount)
        assertEquals(2000.0, domain.volume1HrsUsd.orZero(), 0.0)
        assertEquals(40000.0, domain.volume1DayUsd.orZero(), 0.0)
        assertEquals(600000.0, domain.volume1MthUsd.orZero(), 0.0)
        assertEquals(listOf("METRIC2"), domain.metricId)
        assertEquals(2, domain.rank)
        assertEquals("ACTIVE", domain.integrationStatus)
        assertEquals(1, domain.icons?.size)
        assertEquals("ICON2", domain.icons?.first()?.exchangeId)
        assertEquals("ASSET2", domain.icons?.first()?.assetId)
        assertEquals("url2.com", domain.icons?.first()?.url)
    }

    @Test
    fun `mapRemoteToEntity should correctly map ExchangeResponse to ExchangeEntity`() {
        val response = ExchangeResponse(
            exchangeId = "EXCH3",
            website = "website3.com",
            name = "Exchange Three",
            dataStart = "2023-03-01",
            dataEnd = "2023-03-31",
            dataQuoteStart = "2023-03-01",
            dataQuoteEnd = "2023-03-31",
            dataOrderBookStart = "2023-03-01",
            dataOrderBookEnd = "2023-03-31",
            dataTradeStart = "2023-03-01",
            dataTradeEnd = "2023-03-31",
            dataTradeCount = 300,
            dataSymbolsCount = 30,
            volume1HrsUsd = 3000.0,
            volume1DayUsd = 60000.0,
            volume1MthUsd = 900000.0,
            metricId = listOf("METRIC3"),
            rank = 3,
            icons = null,
            integrationStatus = "INACTIVE"
        )

        val entity = ExchangeMapper.mapRemoteToEntity(response)

        assertEquals("EXCH3", entity.id)
        assertEquals("website3.com", entity.website)
        assertEquals("Exchange Three", entity.name)
        assertEquals("2023-03-01", entity.dataStart)
        assertEquals("2023-03-31", entity.dataEnd)
        assertEquals("2023-03-01", entity.dataQuoteStart)
        assertEquals("2023-03-31", entity.dataQuoteEnd)
        assertEquals("2023-03-01", entity.dataOrderBookStart)
        assertEquals("2023-03-31", entity.dataOrderBookEnd)
        assertEquals("2023-03-01", entity.dataTradeStart)
        assertEquals("2023-03-31", entity.dataTradeEnd)
        assertEquals(300L, entity.dataTradeCount)
        assertEquals(30L, entity.dataSymbolsCount)
        assertEquals(3000.0, entity.volume1HrsUsd.orZero(), 0.0)
        assertEquals(60000.0, entity.volume1DayUsd.orZero(), 0.0)
        assertEquals(900000.0, entity.volume1MthUsd.orZero(), 0.0)
        assertEquals(listOf("METRIC3"), entity.metricId)
        assertEquals(3, entity.rank)
        assertEquals("INACTIVE", entity.integrationStatus)
    }

    @Test
    fun `mapDomainToEntity should correctly map Exchange to ExchangeEntity`() {
        val domain = Exchange(
            exchangeId = "EXCH4",
            website = "website4.com",
            name = "Exchange Four",
            dataStart = "2023-04-01",
            dataEnd = "2023-04-30",
            dataQuoteStart = "2023-04-01",
            dataQuoteEnd = "2023-04-30",
            dataOrderBookStart = "2023-04-01",
            dataOrderBookEnd = "2023-04-30",
            dataTradeStart = "2023-04-01",
            dataTradeEnd = "2023-04-30",
            dataTradeCount = 400,
            dataSymbolsCount = 40,
            volume1HrsUsd = 4000.0,
            volume1DayUsd = 80000.0,
            volume1MthUsd = 1200000.0,
            metricId = listOf("METRIC4"),
            rank = 4,
            icons = null,
            integrationStatus = "PENDING"
        )

        val entity = ExchangeMapper.mapDomainToEntity(domain)

        assertEquals("EXCH4", entity.id)
        assertEquals("website4.com", entity.website)
        assertEquals("Exchange Four", entity.name)
        assertEquals("2023-04-01", entity.dataStart)
        assertEquals("2023-04-30", entity.dataEnd)
        assertEquals("2023-04-01", entity.dataQuoteStart)
        assertEquals("2023-04-30", entity.dataQuoteEnd)
        assertEquals("2023-04-01", entity.dataOrderBookStart)
        assertEquals("2023-04-30", entity.dataOrderBookEnd)
        assertEquals("2023-04-01", entity.dataTradeStart)
        assertEquals("2023-04-30", entity.dataTradeEnd)
        assertEquals(400L, entity.dataTradeCount)
        assertEquals(40L, entity.dataSymbolsCount)
        assertEquals(4000.0, entity.volume1HrsUsd.orZero(), 0.0)
        assertEquals(80000.0, entity.volume1DayUsd.orZero(), 0.0)
        assertEquals(1200000.0, entity.volume1MthUsd.orZero(), 0.0)
        assertEquals(listOf("METRIC4"), entity.metricId)
        assertEquals(4, entity.rank)
        assertEquals("PENDING", entity.integrationStatus)
    }

    @Test
    fun `mapRemoteToDomain should handle null icons correctly`() {
        val response = ExchangeResponse(
            exchangeId = "EXCH5",
            website = "website5.com",
            name = "Exchange Five",
            dataStart = "2023-05-01",
            dataEnd = "2023-05-31",
            dataQuoteStart = "2023-05-01",
            dataQuoteEnd = "2023-05-31",
            dataOrderBookStart = "2023-05-01",
            dataOrderBookEnd = "2023-05-31",
            dataTradeStart = "2023-05-01",
            dataTradeEnd = "2023-05-31",
            dataTradeCount = 500,
            dataSymbolsCount = 50,
            volume1HrsUsd = 5000.0,
            volume1DayUsd = 100000.0,
            volume1MthUsd = 1500000.0,
            metricId = listOf("METRIC5"),
            rank = 5,
            icons = null,
            integrationStatus = "DISABLED"
        )

        val domain = ExchangeMapper.mapRemoteToDomain(response)

        assertEquals("EXCH5", domain.exchangeId)
        assertEquals(null, domain.icons)
        assertEquals(5000.0, domain.volume1HrsUsd.orZero(), 0.0)
        assertEquals(100000.0, domain.volume1DayUsd.orZero(), 0.0)
        assertEquals(1500000.0, domain.volume1MthUsd.orZero(), 0.0)
    }
}
