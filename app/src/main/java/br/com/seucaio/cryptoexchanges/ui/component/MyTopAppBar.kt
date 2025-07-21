package br.com.seucaio.cryptoexchanges.ui.component

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.seucaio.cryptoexchanges.ui.theme.CryptoExchangesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    screenTitle: String,
    showBackButton: Boolean,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(screenTitle) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MyTopAppBarPreview() {
    val navController = rememberNavController()
    CryptoExchangesTheme {
        MyTopAppBar(
            screenTitle = "Screen Title",
            showBackButton = true,
            navController = navController
        )
    }
}