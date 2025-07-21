package br.com.seucaio.cryptoexchanges.ui.screen

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.seucaio.cryptoexchanges.data.error.DataUpdateFailedWarning
import br.com.seucaio.cryptoexchanges.domain.model.Exchange
import br.com.seucaio.cryptoexchanges.domain.usecase.GetExchangesUseCase
import br.com.seucaio.cryptoexchanges.ui.model.ExchangeDetailsData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExchangeViewModel(
    private val getExchangesUseCase: GetExchangesUseCase,
    val snackBarHostState: SnackbarHostState = SnackbarHostState()
) : ViewModel() {
    private val _uiState = MutableStateFlow(ExchangeUiState())
    val uiState: StateFlow<ExchangeUiState> = _uiState

    private val _uiAction = MutableSharedFlow<ExchangeUiAction>()
    val uiAction: SharedFlow<ExchangeUiAction> = _uiAction

    init {
        getExchanges()
    }

    fun getExchanges(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            val isPullToRefresh = forceRefresh

            _uiState.update { currentState ->
                if (isPullToRefresh) {
                    currentState.setRefreshing(true)
                } else {
                    currentState.setLoading(true)
                }
            }

            getExchangesUseCase(forceRefresh)
                .catch { error -> handleError(error) }
                .collectLatest { exchanges -> handleSuccess(exchanges) }
        }
    }

    private fun handleSuccess(exchanges: List<Exchange>) {
        _uiState.update { it.setExchanges(exchanges) }
    }

    private fun handleError(e: Throwable) {
        if (e is DataUpdateFailedWarning) {
            handleDataUpdateFailedWarning(e)
        } else {
            _uiState.update { it.setError(error = e.message.orEmpty()) }
        }

    }

    private fun handleDataUpdateFailedWarning(e: Throwable) {
        val warningMessage = e.message ?: "Não foi possível atualizar os dados."
        _uiState.update { it.setMessage(warningMessage) }
        showSnackBar(message = warningMessage)
    }

    private fun showSnackBar(message: String, actionLabel: String? = null) {
        viewModelScope.launch {
            snackBarHostState.showSnackbar(message = message, actionLabel = actionLabel)
        }
    }

    fun onRefreshExchanges() {
        getExchanges(forceRefresh = true)
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