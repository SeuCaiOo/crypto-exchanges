package br.com.seucaio.cryptoexchanges.domain.model

data class Exchange(
    val exchangeId: String? = null,
    val website: String? = null,
    val name: String? = null,
    val dataStart: String? = null,
    val dataEnd: String? = null,
    val dataQuoteStart: String? = null,
    val dataQuoteEnd: String? = null,
    val dataOrderBookStart: String? = null,
    val dataOrderBookEnd: String? = null,
    val dataTradeStart: String? = null,
    val dataTradeEnd: String? = null,
    val dataTradeCount: Long? = null,
    val dataSymbolsCount: Long? = null,
    val volume1HrsUsd: Double? = null,
    val volume1DayUsd: Double? = null,
    val volume1MthUsd: Double? = null,
    val metricId: List<String>? = null,
    val rank: Int? = null,
    val icons: List<Icon>? = null,
    val integrationStatus: String? = null
) {
    data class Icon(
        val exchangeId: String? = null,
        val assetId: String? = null,
        val url: String? = null
    )

    companion object {
        fun getExchangeSample(): Exchange {
            val exchangeId = "BINANCE"
            return Exchange(
                exchangeId = exchangeId,
                website = "https://www.binance.com/",
                name = "Binance",
                dataQuoteStart = "2017-07-14T04:00:00.0000000Z",
                dataQuoteEnd = "2023-11-20T00:00:00.0000000Z",
                dataOrderBookStart = "2017-07-14T04:00:00.0000000Z",
                dataOrderBookEnd = "2020-08-05T14:38:38.3413202Z",
                dataTradeStart = "2017-07-14T04:00:00.0000000Z",
                dataTradeEnd = "2023-11-20T00:00:00.0000000Z",
                dataSymbolsCount = 2429,
                volume1HrsUsd = 2891234567.89,
                volume1DayUsd = 15234567890.12,
                volume1MthUsd = 456789012345.67,
                integrationStatus = "Active",
                dataTradeCount = 1234567,
                dataStart = "2012",
                rank = 2,
                icons = listOf(
                    Icon(
                        exchangeId = exchangeId,
                        assetId = null,
                        url = "https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_32/3d5c1eb5d5625f6c9f8d8d9f2f7e6c0b.png"
                    )
                )
            )
        }
    }
}