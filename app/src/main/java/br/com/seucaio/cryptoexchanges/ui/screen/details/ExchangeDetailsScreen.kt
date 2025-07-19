package br.com.seucaio.cryptoexchanges.ui.screen.details

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.seucaio.cryptoexchanges.domain.model.Exchange
import br.com.seucaio.cryptoexchanges.ui.CryptoExchangeSurface
import br.com.seucaio.cryptoexchanges.ui.component.MyError
import br.com.seucaio.cryptoexchanges.ui.component.MyLoading
import br.com.seucaio.cryptoexchanges.ui.model.DetailItem
import br.com.seucaio.cryptoexchanges.ui.model.DetailRowData
import br.com.seucaio.cryptoexchanges.ui.model.ExchangeDetailsData
import br.com.seucaio.cryptoexchanges.ui.model.ExchangeDetailsData.Companion.getExchangeDetailsDataSample
import br.com.seucaio.cryptoexchanges.ui.screen.ExchangeStateRenderer
import br.com.seucaio.cryptoexchanges.ui.screen.ExchangeUiState
import br.com.seucaio.cryptoexchanges.ui.screen.ExchangeViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeDetailsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExchangeViewModel = koinViewModel(),
) {
    val uiState: ExchangeUiState by viewModel.uiState.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        onDispose {
            viewModel.setExchangeDetails(null)
        }
    }

    uiState.ExchangeStateRenderer(
        loadingContent = { MyLoading(modifier = modifier) },
        errorContent = {
            MyError(
                modifier = modifier,
                title = "Oops! Algo deu errado.",
                errorMessage = uiState.error.orEmpty(),
                onRetry = { onNavigateBack() }
            )
        },
        successContent = {
            ExchangeDetailsContent(
                modifier = modifier,
                detailsData = uiState.exchangeDetailsData ?: getExchangeDetailsDataSample(),
            )
        }
    )
}

// --- Conteúdo da Tela de Detalhes ---

@Composable
private fun ExchangeDetailsContent(
    detailsData: ExchangeDetailsData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(detailsData.logoUrl)
                .crossfade(true)
//                .placeholder(R.drawable.img_logo_placeholder) // Crie este drawable placeholder
//                .error(R.drawable.img_logo_placeholder) // Ou um drawable de erro específico
                .build(),
            contentDescription = "${detailsData.exchangeName} Logo",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentScale = ContentScale.Crop // Ou ContentScale.Fit se a imagem não for naturalmente circular
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nome da Exchange
        Text(
            text = detailsData.exchangeName,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        // ID da Exchange
        Text(
            text = detailsData.exchangeIdLabel,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant, // Cor mais suave
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Seção de Volumes
        SectionBlock(title = "Volumes", details = detailsData.volumeDetails)

        Spacer(modifier = Modifier.height(32.dp))

        // Seção de Informações
        SectionBlock(title = "Information", details = detailsData.informationDetails)

        Spacer(modifier = Modifier.height(16.dp)) // Espaço no final
    }
}

// --- Componentes Reutilizáveis ---

@Composable
private fun SectionBlock(
    title: String,
    details: List<DetailRowData>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        details.forEachIndexed { index, rowData ->
            DetailInfoRow(data = rowData)
            if (index < details.lastIndex) { // Adiciona divisor entre os itens, mas não após o último
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
private fun DetailInfoRow(
    data: DetailRowData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DetailInfoItem(item = data.item1, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(16.dp))
        if (data.item2 != null) {
            DetailInfoItem(item = data.item2, modifier = Modifier.weight(1f))
        } else {
            Spacer(modifier = Modifier.weight(1f)) // Para manter o alinhamento se não houver item2
        }
    }
}

@Composable
private fun DetailInfoItem(
    item: DetailItem,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = item.label,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
        )
    }
}


// --- Previews ---

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewExchangeDetailsContent() {
    CryptoExchangeSurface {
        ExchangeDetailsContent(
            detailsData = getExchangeDetailsDataSample(),
        )
    }
}
