package br.com.seucaio.cryptoexchanges.ui.model

import br.com.seucaio.cryptoexchanges.core.extension.orZero
import br.com.seucaio.cryptoexchanges.core.utils.asAbbreviationValue
import br.com.seucaio.cryptoexchanges.domain.model.Exchange

data class ExchangeItemData(
    val uniqueId: String,
    val name: String,
    val volumeLabel: String
) {
    constructor(exchange: Exchange) : this(
        uniqueId = exchange.exchangeId.orEmpty(),
        name = exchange.name.orEmpty(),
        volumeLabel = exchange.volume1DayUsd.orZero().asAbbreviationValue()
    )

    companion object {
        fun getSampleExchangeData(): List<ExchangeItemData> {
            return listOf(
                ExchangeItemData("COINBASE", "Coinbase", "$1.2B"),
                ExchangeItemData("BINANCE", "Binance", "$950M"),
                ExchangeItemData("KRAKEN", "Kraken", "$750M"),
                ExchangeItemData("GEMINI", "Gemini", "$500M"),
                ExchangeItemData("HUOBI", "Huobi", "$400M"),
                ExchangeItemData("OKEX", "OKEx", "$350M"),
                ExchangeItemData("BITFINEX", "Bitfinex", "$300M"),
                ExchangeItemData("KUCOPIN", "KuCoin", "$250M"),
                ExchangeItemData("BITSTAMP", "Bitstamp", "$200M"),
                ExchangeItemData("GATEIO", "Gate.io", "$150M")
            )
        }
    }
}