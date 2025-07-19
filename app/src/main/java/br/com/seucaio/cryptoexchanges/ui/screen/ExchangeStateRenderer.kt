package br.com.seucaio.cryptoexchanges.ui.screen

import androidx.compose.runtime.Composable

@Composable
fun ExchangeUiState.ExchangeStateRenderer(
    loadingContent: @Composable () -> Unit,
    errorContent: @Composable () -> Unit,
    successContent: @Composable () -> Unit,
) {
    when {
        isLoading -> loadingContent()
        error != null -> errorContent()
        else -> successContent()
    }
}