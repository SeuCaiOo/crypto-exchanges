package br.com.seucaio.cryptoexchanges.domain.repository

import br.com.seucaio.cryptoexchanges.domain.model.Exchange
import kotlinx.coroutines.flow.Flow

interface ExchangeRepository {
    fun getExchanges(): Flow<List<Exchange>>
}