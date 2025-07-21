package br.com.seucaio.cryptoexchanges.data.remote

import br.com.seucaio.cryptoexchanges.data.remote.dto.ExchangeResponse

fun createExchangeResponseSample(
    exchangeId: String? = "API_EXCHANGE",
    name: String? = "API Exchange Name",
    website: String? = "https://api.exchange.com",
    dataStart: String? = "2019-01-01",
    volume1DayUsd: Double? = 2000000.0,
    rank: Int? = 5,
    icons: List<ExchangeResponse.IconResponse>? = listOf(createIconResponseSample(exchangeId = exchangeId))
): ExchangeResponse {
    return ExchangeResponse(
        exchangeId = exchangeId,
        website = website,
        name = name,
        dataStart = dataStart,
        dataEnd = "2023-11-30",
        dataQuoteStart = "2019-01-01T00:00:00.0000000Z",
        dataQuoteEnd = "2023-11-30T00:00:00.0000000Z",
        dataOrderBookStart = "2019-01-01T00:00:00.0000000Z",
        dataOrderBookEnd = "2023-11-30T00:00:00.0000000Z",
        dataTradeStart = "2019-01-01T00:00:00.0000000Z",
        dataTradeEnd = "2023-11-30T00:00:00.0000000Z",
        dataTradeCount = 654321L,
        dataSymbolsCount = 50L,
        volume1HrsUsd = 100000.0,
        volume1DayUsd = volume1DayUsd,
        volume1MthUsd = 60000000.0,
        metricId = listOf("api_metric_1"),
        rank = rank,
        icons = icons,
        integrationStatus = "Inactive"
    )
}

fun createIconResponseSample(
    exchangeId: String? = "API_EXCHANGE",
    assetId: String? = "ETH",
    url: String? = "https://api.example.com/icon.png"
): ExchangeResponse.IconResponse {
    return ExchangeResponse.IconResponse(
        exchangeId = exchangeId,
        assetId = assetId,
        url = url
    )
}

fun createExchangeResponseListSample(count: Int = 2): List<ExchangeResponse> {
    return List(count) { i ->
        createExchangeResponseSample(
            exchangeId = "API_EXCHANGE_$i",
            name = "API Exchange $i",
            rank = 10 + i
        )
    }
}