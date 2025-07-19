package br.com.seucaio.cryptoexchanges.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import br.com.seucaio.cryptoexchanges.ui.component.MyError
import br.com.seucaio.cryptoexchanges.ui.screen.ExchangeViewModel
import br.com.seucaio.cryptoexchanges.ui.screen.details.ExchangeDetailsScreen
import br.com.seucaio.cryptoexchanges.ui.screen.list.ExchangeListScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.ExchangeFlow,
        modifier = modifier
    ) {
        val popBackStack = navController.popBackStack()

        composable<AppRoutes.Error> { MyError(onRetry = { popBackStack }) }

        navigation<AppRoutes.ExchangeFlow>(startDestination = AppRoutes.ExchangeList) {
            composable<AppRoutes.ExchangeList> {backStackEntry ->
                val parentEntry: NavBackStackEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(AppRoutes.ExchangeFlow::class)
                }
                val sharedViewModel: ExchangeViewModel =
                    koinViewModel(viewModelStoreOwner = parentEntry)

                ExchangeListScreen(
                    onNavigateToDetails = {
                        navController.navigate(route = AppRoutes.ExchangeDetails)
                    },
                    viewModel = sharedViewModel
                )
            }

            composable<AppRoutes.ExchangeDetails> {backStackEntry ->
                val parentEntry: NavBackStackEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(AppRoutes.ExchangeFlow::class)
                }
                val sharedViewModel: ExchangeViewModel =
                    koinViewModel(viewModelStoreOwner = parentEntry)

                ExchangeDetailsScreen(
                    onNavigateBack = { popBackStack },
                    viewModel = sharedViewModel,
                )
            }
        }
    }
}