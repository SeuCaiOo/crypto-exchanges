package br.com.seucaio.cryptoexchanges.domain.usecase

import br.com.seucaio.cryptoexchanges.domain.model.Exchange
import br.com.seucaio.cryptoexchanges.domain.repository.ExchangeRepository
import kotlinx.coroutines.flow.Flow

class GetExchangesUseCase(private val repository: ExchangeRepository) {
    operator fun invoke(forceRefresh: Boolean = false): Flow<List<Exchange>> {
        return repository.getExchanges(forceRefresh)
    }
}