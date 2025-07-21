package br.com.seucaio.cryptoexchanges.domain

import br.com.seucaio.cryptoexchanges.domain.model.Exchange

fun createExchangeSample(
    exchangeId: String = "TEST_EXCHANGE",
    name: String = "Test Exchange",
    website: String = "https://www.testexchange.com",
    dataStart: String = "2020-01-01",
    volume1DayUsd: Double = 1000000.0,
    rank: Int = 1,
    icons: List<Exchange.Icon> = listOf(createExchangeIconSample(exchangeId = exchangeId))
): Exchange {
    return Exchange(
        exchangeId = exchangeId,
        website = website,
        name = name,
        dataStart = dataStart,
        dataEnd = "2023-12-31",
        dataQuoteStart = "2020-01-01T00:00:00.0000000Z",
        dataQuoteEnd = "2023-12-31T00:00:00.0000000Z",
        dataOrderBookStart = "2020-01-01T00:00:00.0000000Z",
        dataOrderBookEnd = "2023-12-31T00:00:00.0000000Z",
        dataTradeStart = "2020-01-01T00:00:00.0000000Z",
        dataTradeEnd = "2023-12-31T00:00:00.0000000Z",
        dataTradeCount = 123456L,
        dataSymbolsCount = 100L,
        volume1HrsUsd = 50000.0,
        volume1DayUsd = volume1DayUsd,
        volume1MthUsd = 30000000.0,
        metricId = listOf("metric1", "metric2"),
        rank = rank,
        icons = icons,
        integrationStatus = "Active"
    )
}

fun createExchangeIconSample(
    exchangeId: String? = "TEST_EXCHANGE",
    assetId: String? = "BTC",
    url: String = "https://example.com/icon.png"
): Exchange.Icon {
    return Exchange.Icon(
        exchangeId = exchangeId,
        assetId = assetId,
        url = url
    )
}

fun createExchangeListSample(count: Int = 3): List<Exchange> {
    return List(count) { i ->
        createExchangeSample(
            exchangeId = "TEST_EXCHANGE_$i",
            name = "Test Exchange $i",
            rank = i + 1
        )
    }
}