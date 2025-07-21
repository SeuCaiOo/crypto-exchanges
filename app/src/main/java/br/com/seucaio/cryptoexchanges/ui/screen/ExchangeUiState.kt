package br.com.seucaio.cryptoexchanges.ui.screen

import br.com.seucaio.cryptoexchanges.domain.model.Exchange
import br.com.seucaio.cryptoexchanges.ui.model.ExchangeDetailsData
import br.com.seucaio.cryptoexchanges.ui.model.ExchangeItemData

data class ExchangeUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val message: String? = null,
    val exchanges: List<Exchange> = emptyList(),
    val items: List<ExchangeItemData> = emptyList(),
    val exchangeDetailsData: ExchangeDetailsData? = null
) {
    fun setLoading(loading: Boolean): ExchangeUiState {
        return copy(isLoading = loading, error = null, isRefreshing = false)
    }

    fun setRefreshing(refreshing: Boolean): ExchangeUiState {
        return copy(isRefreshing = refreshing, error = null, isLoading = false)
    }

    fun setError(error: String): ExchangeUiState {
        return this.copy(error = error, isLoading = false, isRefreshing = false)
    }

    fun setMessage(message: String?): ExchangeUiState {
        message ?: return this
        return this.copy(message = message, isLoading = false, isRefreshing = false, error = null)
    }

    fun setExchanges(exchangeList: List<Exchange>): ExchangeUiState {
        exchangeList.ifEmpty { return this.setError(error = "Nenhum item encontrado") }

        val itemData: List<ExchangeItemData> = exchangeList.map { ExchangeItemData(it) }

        return copy(
            items = itemData,
            exchanges = exchangeList,
            isLoading = false,
            error = null,
            isRefreshing = false
        )
    }

    fun setExchangeDetailsData(exchangeDetailsData: ExchangeDetailsData?): ExchangeUiState {
        return this.copy(
            exchangeDetailsData = exchangeDetailsData,
            isLoading = false,
            error = null,
            isRefreshing = false
        )
    }
}