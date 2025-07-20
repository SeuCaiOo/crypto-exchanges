package br.com.seucaio.cryptoexchanges.data.repository

import br.com.seucaio.cryptoexchanges.data.mapper.ExchangeResponseMapper
import br.com.seucaio.cryptoexchanges.data.source.ExchangeDataSource
import br.com.seucaio.cryptoexchanges.domain.model.Exchange
import br.com.seucaio.cryptoexchanges.domain.repository.ExchangeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExchangeRepositoryImpl(
    private val dataSource: ExchangeDataSource,
    private val mapper: ExchangeResponseMapper = ExchangeResponseMapper
) : ExchangeRepository {
    override fun getExchanges(): Flow<List<Exchange>> {
        return dataSource.getExchanges().map { list -> list.map(mapper::map) }
    }
}