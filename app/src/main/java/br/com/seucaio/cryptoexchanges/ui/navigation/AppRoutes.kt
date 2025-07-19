package br.com.seucaio.cryptoexchanges.ui.navigation

import kotlinx.serialization.Serializable

object AppRoutes {
    // TODO Remover no final;
    @Serializable
    data object Home

    // TODO Remover no final;
    @Serializable
    data class Error(
        val title: String? = null,
        val message: String? = null,
    )

    @Serializable
    data object ExchangeFlow

    @Serializable
    data object ExchangeList

    @Serializable
    data object ExchangeDetails
}