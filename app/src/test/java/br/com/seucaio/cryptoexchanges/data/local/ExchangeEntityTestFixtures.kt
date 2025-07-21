package br.com.seucaio.cryptoexchanges.data.local

import br.com.seucaio.cryptoexchanges.data.local.entity.ExchangeEntity

fun createExchangeEntitySample(
    id: String = "DB_EXCHANGE_ID",
    name: String? = "DB Exchange Name",
    website: String? = "https://db.exchange.org",
    dataStart: String? = "2021-06-01",
    volume1DayUsd: Double? = 500000.0,
    rank: Int? = 3,
    createdAt: Long = System.currentTimeMillis()
): ExchangeEntity {
    return ExchangeEntity(
        id = id,
        website = website,
        name = name,
        dataStart = dataStart,
        dataEnd = "2023-10-31",
        dataQuoteStart = "2021-06-01T00:00:00.0000000Z",
        dataQuoteEnd = "2023-10-31T00:00:00.0000000Z",
        dataOrderBookStart = "2021-06-01T00:00:00.0000000Z",
        dataOrderBookEnd = "2023-10-31T00:00:00.0000000Z",
        dataTradeStart = "2021-06-01T00:00:00.0000000Z",
        dataTradeEnd = "2023-10-31T00:00:00.0000000Z",
        dataTradeCount = 98765L,
        dataSymbolsCount = 75L,
        volume1HrsUsd = 25000.0,
        volume1DayUsd = volume1DayUsd,
        volume1MthUsd = 15000000.0,
        metricId = listOf("db_metric_a", "db_metric_b"),
        rank = rank,
        integrationStatus = "Active",
        createdAt = createdAt
    )
}

fun createExchangeEntityListSample(count: Int = 5): List<ExchangeEntity> {
    return List(count) { i ->
        createExchangeEntitySample(
            id = "DB_EXCHANGE_ID_$i",
            name = "DB Exchange $i",
            rank = (i + 1) * 2
        )
    }
}