package br.com.seucaio.cryptoexchanges.ui.screen.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.seucaio.cryptoexchanges.core.extension.orEmpty
import br.com.seucaio.cryptoexchanges.ui.CryptoExchangeSurface
import br.com.seucaio.cryptoexchanges.ui.model.ExchangeItemData
import br.com.seucaio.cryptoexchanges.ui.component.MyError
import br.com.seucaio.cryptoexchanges.ui.component.MyLoading
import br.com.seucaio.cryptoexchanges.ui.model.ExchangeItemData.Companion.getSampleExchangeData
import br.com.seucaio.cryptoexchanges.ui.screen.ExchangeStateRenderer
import br.com.seucaio.cryptoexchanges.ui.screen.ExchangeUiAction
import br.com.seucaio.cryptoexchanges.ui.screen.ExchangeUiState
import br.com.seucaio.cryptoexchanges.ui.screen.ExchangeViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeListScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetails: () -> Unit,
    viewModel: ExchangeViewModel = koinViewModel(),
) {
    val uiState: ExchangeUiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getExchanges()
    }

    LaunchedEffect(Unit) {
        viewModel.uiAction.collect { action ->
            when (action) {
                is ExchangeUiAction.NavigateExchangeDetails -> onNavigateToDetails()
            }
        }
    }

    ExchangeListContent(
        uiState = uiState,
        onRetryClicked = { viewModel.getExchanges() },
        onNavigateToDetails = viewModel::onItemClicked,
        modifier = modifier
    )
}

@Composable
fun ExchangeListContent(
    uiState: ExchangeUiState,
    onRetryClicked: () -> Unit,
    onNavigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    uiState.ExchangeStateRenderer(
        loadingContent = { MyLoading(modifier = modifier) },
        errorContent = {
            MyError(
                modifier = modifier,
                title = "Oops! Algo deu errado.",
                errorMessage = uiState.error.orEmpty(),
                onRetry = onRetryClicked
            )
        },
        successContent = {
            ExchangeListSuccess(
                uiState = uiState,
                onNavigateToDetails = onNavigateToDetails,
                modifier = modifier
            )
        }
    )
}

@Composable
fun ExchangeListSuccess(
    uiState: ExchangeUiState,
    onNavigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = uiState.items, key = { it.uniqueId }) { exchange ->
            ExchangeListItem(
                data = exchange,
                onItemClick = { onNavigateToDetails(exchange.uniqueId) }
            )
        }
    }
}

@Composable
fun ExchangeListItem(
    data: ExchangeItemData,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onItemClick
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = data.name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = data.uniqueId,
                    style = MaterialTheme.typography.bodyMedium, // Ou titleSmall
                )
            }
            Text(
                text = data.volumeLabel,
                style = MaterialTheme.typography.bodyLarge, // Ou titleMedium
            )
        }
    }
}

// --- Previews ---


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewExchangeListContentLoading() {
    CryptoExchangeSurface {
        val sampleUiState = ExchangeUiState(isLoading = true)
        ExchangeListContent(
            uiState = sampleUiState,
            onRetryClicked = {},
            onNavigateToDetails = {}
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewExchangeListContentError() {
    CryptoExchangeSurface {
        val sampleUiState = ExchangeUiState(error = "Erro de conexão")
        ExchangeListContent(
            uiState = sampleUiState,
            onRetryClicked = {},
            onNavigateToDetails = {}
        )
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewExchangeListContent() {
    CryptoExchangeSurface {
        val sampleUiState = ExchangeUiState(items = getSampleExchangeData())
        ExchangeListContent(
            uiState = sampleUiState,
            onRetryClicked = {},
            onNavigateToDetails = {}
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewExchangeListItem() {
    CryptoExchangeSurface {
        ExchangeListItem(
            data = ExchangeItemData(
                "EXCHANGE",
                "Preview Exchange",
                "$999M"
            ),
            onItemClick = {}
        )
    }
}
