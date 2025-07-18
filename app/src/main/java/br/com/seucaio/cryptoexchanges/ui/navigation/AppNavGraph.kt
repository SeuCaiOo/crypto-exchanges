package br.com.seucaio.cryptoexchanges.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.Home,
        modifier = modifier
    ) {
        composable<AppRoutes.Home> {}
        composable<AppRoutes.Error> {}
        composable<AppRoutes.ExchangeList> {}
        composable<AppRoutes.ExchangeDetails> {}
    }
}