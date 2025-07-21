package br.com.seucaio.cryptoexchanges.ui.model

import br.com.seucaio.cryptoexchanges.core.extension.orZero
import br.com.seucaio.cryptoexchanges.core.extension.toStringSafe
import br.com.seucaio.cryptoexchanges.core.utils.formatter.asFullValue
import br.com.seucaio.cryptoexchanges.core.utils.formatter.toFormattedDateString
import br.com.seucaio.cryptoexchanges.domain.model.Exchange

data class ExchangeDetailsData(
    val exchangeName: String? = null,
    val exchangeIdLabel: String? = null,
    val logoUrl: String? = null,
    val volumeDetails: List<DetailRowData> = emptyList(),
    val informationDetails: List<DetailRowData> = emptyList()
) {
    constructor(exchange: Exchange) : this(
        exchangeName = exchange.name,
        exchangeIdLabel = exchange.exchangeId,
        logoUrl = exchange.icons?.firstOrNull()?.url,
        volumeDetails = listOf(
            DetailRowData(
                item1 = DetailItem(
                    label = "Volume (1h)",
                    value = exchange.volume1HrsUsd.orZero().asFullValue()
                ),
            ),
            DetailRowData(
                item1 =
                    DetailItem(
                        label = "Volume (1d)",
                        value = exchange.volume1DayUsd.orZero().asFullValue()
                    )
            ),
            DetailRowData(
                item1 =
                    DetailItem(
                        label = "Volume (30d)",
                        value = exchange.volume1MthUsd.orZero().asFullValue()
                    )
            )
        ),
        informationDetails = listOf(
            DetailRowData(DetailItem(label = "Rank", value = exchange.rank.toStringSafe())),
            DetailRowData(DetailItem(label = "Status", value = exchange.integrationStatus)),
            DetailRowData(DetailItem(label = "Trades", value = exchange.dataTradeCount.toStringSafe())),
            DetailRowData(
                DetailItem(
                    label = "Start",
                    value = exchange.dataStart?.toFormattedDateString()
                )
            ),
            DetailRowData(
                DetailItem(label = "Symbols", value = exchange.dataSymbolsCount.toStringSafe())
            ),
            DetailRowData(
                DetailItem(
                    label = "End",
                    value = exchange.dataEnd?.toFormattedDateString()
                )
            ),
            DetailRowData(
                DetailItem(
                    label = "OrderBook Start",
                    value = exchange.dataOrderBookStart?.toFormattedDateString()
                )
            ),
            DetailRowData(
                DetailItem(
                    label = "OrderBook End",
                    value = exchange.dataOrderBookEnd?.toFormattedDateString()
                )
            ),
            DetailRowData(
                DetailItem(
                    label = "Quote Start",
                    value = exchange.dataQuoteStart?.toFormattedDateString()
                )
            ),
            DetailRowData(
                DetailItem(
                    label = "Quote End",
                    value = exchange.dataQuoteEnd?.toFormattedDateString()
                )
            )
            ),
    )


    companion object {
        fun getExchangeDetailsDataSample(): ExchangeDetailsData {
            return ExchangeDetailsData(
                exchangeName = "Coinbase",
                exchangeIdLabel = "Exchange ID: 12345",
                logoUrl = "https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_32/3d5c1eb5d5625f6c9f8d8d9f2f7e6c0b.png", // Usará placeholder
                volumeDetails = listOf(
                    DetailRowData(DetailItem(label = "Volume (1h)", value = "$100,000")),
                    DetailRowData(DetailItem(label = "Volume (24h)", value = "$2,400,000")),
                    DetailRowData(DetailItem(label = "Volume (30d)", value = "$72,000,000")),
                ),
                informationDetails = listOf(
                    DetailRowData(DetailItem(label = "Rank", value = "#1")),
                    DetailRowData(DetailItem(label = "Status", value = "Active")),
                    DetailRowData(DetailItem(label = "Trades", value = null)),
                    DetailRowData(DetailItem(label = "Start", value = "2012")),
                    DetailRowData(DetailItem(label = "End", value = "2023")),
                    DetailRowData(DetailItem(label = "Symbols", value = "1234")),
                    DetailRowData(DetailItem(label = "OrderBook Start", value = "2012")),
                    DetailRowData(DetailItem(label = "OrderBook End", value = "2023")),
                )
            )
        }
    }
}

data class DetailRowData(val item1: DetailItem, val item2: DetailItem? = null)

data class DetailItem(val label: String, val value: String?)