package br.com.seucaio.cryptoexchanges.ui.navigation

import kotlinx.serialization.Serializable

object AppRoutes {
    @Serializable
    data object Home

    @Serializable
    data class Error(
        val title: String? = null,
        val message: String? = null,
    )

    @Serializable
    data object ExchangeList

    @Serializable
    data class ExchangeDetails(val exchangeId: String)
}