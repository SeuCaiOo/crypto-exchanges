package br.com.seucaio.cryptoexchanges.ui.screen

sealed interface ExchangeUiAction {
    data object NavigateExchangeDetails : ExchangeUiAction
//    data class ShowMessage(val message: String) : ExchangeUiAction
}