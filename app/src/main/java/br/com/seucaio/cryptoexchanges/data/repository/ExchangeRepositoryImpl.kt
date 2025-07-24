package br.com.seucaio.cryptoexchanges.data.repository

import br.com.seucaio.cryptoexchanges.core.utils.network.NetworkException
import br.com.seucaio.cryptoexchanges.data.error.DataUpdateFailedWarning
import br.com.seucaio.cryptoexchanges.data.local.entity.ExchangeEntity
import br.com.seucaio.cryptoexchanges.data.mapper.ExchangeMapper
import br.com.seucaio.cryptoexchanges.data.remote.dto.ExchangeResponse
import br.com.seucaio.cryptoexchanges.data.local.source.ExchangeLocalDataSource
import br.com.seucaio.cryptoexchanges.data.remote.source.ExchangeRemoteDataSource
import br.com.seucaio.cryptoexchanges.domain.model.Exchange
import br.com.seucaio.cryptoexchanges.domain.repository.ExchangeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlin.collections.map

class ExchangeRepositoryImpl(
    private val localDataSource: ExchangeLocalDataSource,
    private val remoteDataSource: ExchangeRemoteDataSource,
    private val mapper: ExchangeMapper = ExchangeMapper
) : ExchangeRepository {
    override fun getExchanges(): Flow<List<Exchange>> {
        return getExchangesFromCache()
            .onStart { refreshExchangesIfNeeded() }
            .flowOn(Dispatchers.IO)
    }

    override fun refreshExchanges(): Flow<List<Exchange>> {
        return getExchangesFromCache()
            .onStart { fetchAndCacheExchangesFromApi() }
            .catch { e -> throw DataUpdateFailedWarning.fromThrowable(e) }
            .flowOn(Dispatchers.IO)
    }

    private fun getExchangesFromCache(): Flow<List<Exchange>> {
        return localDataSource.getAllExchanges().map { list ->
            list.map(mapper::mapLocalToDomain)
        }
    }

    private fun getExchangesFromApi(): Flow<List<Exchange>> {
        return remoteDataSource.getExchanges().map { list ->
            list.map(mapper::mapRemoteToDomain)
        }
    }

    private suspend fun refreshExchangesIfNeeded() {
        val oldestTimestamp = localDataSource.getOldestUpdateTimestamp()
        if (oldestTimestamp == null) fetchAndCacheExchangesFromApi()
    }

    private suspend fun fetchAndCacheExchangesFromApi() {
        try {
            getExchangesFromApi().first().let { exchangesFromApi ->
                if (exchangesFromApi.isNotEmpty()) {
                    localDataSource.clearAndCacheExchanges(
                        exchanges = exchangesFromApi.map(mapper::mapDomainToEntity)
                    )
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }
}