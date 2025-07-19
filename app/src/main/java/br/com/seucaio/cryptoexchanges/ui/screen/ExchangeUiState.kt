package br.com.seucaio.cryptoexchanges.ui.screen

import br.com.seucaio.cryptoexchanges.domain.model.Exchange
import br.com.seucaio.cryptoexchanges.ui.model.ExchangeDetailsData
import br.com.seucaio.cryptoexchanges.ui.model.ExchangeItemData

data class ExchangeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val exchanges: List<Exchange> = emptyList(),
    val items: List<ExchangeItemData> = emptyList(),
    val exchangeDetailsData: ExchangeDetailsData? = null
) {
    fun setLoading(loading: Boolean): ExchangeUiState = this.copy(isLoading = loading)

    fun setError(error: String): ExchangeUiState = this.copy(error = error, isLoading = false)

    fun setExchanges(exchangeList: List<Exchange>): ExchangeUiState {
        exchangeList.ifEmpty { return this.setError(error = "No exchanges found") }

        return exchangeList.map { ExchangeItemData(it) }.let { exchangeItems ->
            this.copy(items = exchangeItems, exchanges = exchangeList, error = null)
        }
    }

    fun setExchangeDetailsData(exchangeDetailsData: ExchangeDetailsData?): ExchangeUiState {
        return this.copy(exchangeDetailsData = exchangeDetailsData, isLoading = false)
    }
}