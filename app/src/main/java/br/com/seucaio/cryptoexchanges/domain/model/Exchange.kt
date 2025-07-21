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
}