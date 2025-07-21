package br.com.seucaio.cryptoexchanges.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeResponse(
    @SerialName("exchange_id")
    val exchangeId: String? = null,
    val website: String? = null,
    val name: String? = null,
    @SerialName("data_start")
    val dataStart: String? = null,
    @SerialName("data_end")
    val dataEnd: String? = null,
    @SerialName("data_quote_start")
    val dataQuoteStart: String? = null,
    @SerialName("data_quote_end")
    val dataQuoteEnd: String? = null,
    @SerialName("data_orderbook_start")
    val dataOrderBookStart: String? = null,
    @SerialName("data_orderbook_end")
    val dataOrderBookEnd: String? = null,
    @SerialName("data_trade_start")
    val dataTradeStart: String? = null,
    @SerialName("data_trade_end")
    val dataTradeEnd: String? = null,
    @SerialName("data_trade_count")
    val dataTradeCount: Long? = null,
    @SerialName("data_symbols_count")
    val dataSymbolsCount: Long? = null,
    @SerialName("volume_1hrs_usd")
    val volume1HrsUsd: Double? = null,
    @SerialName("volume_1day_usd")
    val volume1DayUsd: Double? = null,
    @SerialName("volume_1mth_usd")
    val volume1MthUsd: Double? = null,
    @SerialName("metric_id")
    val metricId: List<String>? = null,
    val rank: Int? = null,
    val icons: List<IconResponse>? = null,
    @SerialName("integration_status")
    val integrationStatus: String? = null
) {
    @Serializable
    data class IconResponse(
        @SerialName("exchange_id")
        val exchangeId: String? = null,
        @SerialName("asset_id")
        val assetId: String? = null,
        val url: String? = null
    )
}
