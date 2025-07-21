package br.com.seucaio.cryptoexchanges.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.seucaio.cryptoexchanges.ui.component.MyTopAppBar
import br.com.seucaio.cryptoexchanges.ui.navigation.AppNavGraph
import br.com.seucaio.cryptoexchanges.ui.theme.CryptoExchangesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { CryptoExchangeApp() }
    }
}

@Composable
fun CryptoExchangeSurface(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    CryptoExchangesTheme {
        Surface(modifier = modifier) {
            content()
        }
    }
}

@Composable
fun CryptoExchangeApp() {
    CryptoExchangesTheme {
        val navController = rememberNavController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route
        val showBackButton = navController.previousBackStackEntry != null

        Scaffold(
            topBar = {
                MyTopAppBar(
                    screenTitle = "Crypto Exchanges",
                    showBackButton = showBackButton,
                    navController = navController
                )
            },
        ) { innerPadding ->
            Surface {
                AppNavGraph(
                    navController = navController,
                    modifier = Modifier
                        .padding(paddingValues = innerPadding)
                        .fillMaxSize()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CryptoExchangeAppPreview() {
    CryptoExchangeApp()
}