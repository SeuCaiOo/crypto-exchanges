package br.com.seucaio.cryptoexchanges.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.seucaio.cryptoexchanges.domain.usecase.GetExchangesUseCase
import br.com.seucaio.cryptoexchanges.ui.model.ExchangeDetailsData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExchangeViewModel(
    private val getExchangesUseCase: GetExchangesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ExchangeUiState())
    val uiState: StateFlow<ExchangeUiState> = _uiState

    private val _uiAction = MutableSharedFlow<ExchangeUiAction>()
    val uiAction: SharedFlow<ExchangeUiAction> = _uiAction

    fun getExchanges() {
        viewModelScope.launch {
            getExchangesUseCase()
                .onStart { _uiState.update { it.setLoading(true) } }
                .catch { error ->
                    _uiState.update { it.setError(error = error.message.orEmpty()) }
                }
                .onCompletion { _uiState.update { it.setLoading(false) } }
                .collect { exchanges -> _uiState.update { it.setExchanges(exchanges) } }
        }
    }


    fun onItemClicked(uniqueId: String) {
        uiState.value.exchanges
            .map { ExchangeDetailsData(it) }
            .firstOrNull { it.exchangeIdLabel == uniqueId }.also { detailsData ->
                setExchangeDetails(detailsData)
                navigateToDetails()
            }
    }

    fun navigateToDetails() {
        emitAction(ExchangeUiAction.NavigateExchangeDetails)
    }

    fun setExchangeDetails(exchangeDetailsData: ExchangeDetailsData?) {
        _uiState.update { it.setExchangeDetailsData(exchangeDetailsData) }
    }

    private fun emitAction(action: ExchangeUiAction) {
        viewModelScope.launch { _uiAction.emit(action) }
    }
}