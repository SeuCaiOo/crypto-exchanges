package br.com.seucaio.cryptoexchanges.ui.model

import br.com.seucaio.cryptoexchanges.domain.model.Exchange

data class ExchangeDetailsData(
    val exchangeName: String = "",
    val exchangeIdLabel: String = "",
    val logoUrl: String? = null,
    val volumeDetails: List<DetailRowData> = emptyList(),
    val informationDetails: List<DetailRowData> = emptyList()
) {
    constructor(exchange: Exchange) : this(
        exchangeName = exchange.name.orEmpty(),
        exchangeIdLabel = exchange.exchangeId.orEmpty(),
        logoUrl = exchange.icons?.firstOrNull()?.url,
        volumeDetails = listOf(
            DetailRowData(
                item1 = DetailItem(label = "Volume (1h)", value = "${exchange.volume1HrsUsd}"),
                item2 = DetailItem(label = "Volume (30d)", value = "${exchange.volume1MthUsd}"),
            )
        ),
        informationDetails = listOf(
            DetailRowData(
                item1 = DetailItem(label = "Rank", value = "${exchange.rank}"),
                item2 = DetailItem(label = "Status", value = "${exchange.integrationStatus}")
            ),
            DetailRowData(
                item1 = DetailItem(label = "Trades", value = "${exchange.dataTradeCount}"),
                item2 = DetailItem(label = "Start", value = "${exchange.dataStart}")
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
                    DetailRowData(
                        item1 = DetailItem(label = "Volume (1h)", value = "$100,000"),
                        item2 = DetailItem(label = "Volume (24h)", value = "$2,400,000")
                    ),
                    DetailRowData(
                        item1 = DetailItem(label = "Volume (30d)", value = "$72,000,000"),
                        item2 = DetailItem(
                            label = "Volume (1h)",
                            value = "$100,000"
                        ) // Exemplo de repetição, ajuste conforme necessário
                    ),
                    DetailRowData( // Adicionando mais uma linha para corresponder à imagem
                        item1 = DetailItem(label = "Volume (24h)", value = "$2,400,000"),
                        item2 = DetailItem(label = "Volume (30d)", value = "$72,000,000")
                    )
                ),
                informationDetails = listOf(
                    DetailRowData(
                        item1 = DetailItem(label = "Rank", value = "#1"),
                        item2 = DetailItem(label = "Status", value = "Active")
                    ),
                    DetailRowData(
                        item1 = DetailItem(label = "Trades", value = "1,234,567"),
                        item2 = DetailItem(label = "Start", value = "2012")
                    )
                )
            )
        }
    }
}

data class DetailRowData(val item1: DetailItem, val item2: DetailItem? = null)

data class DetailItem(val label: String, val value: String)