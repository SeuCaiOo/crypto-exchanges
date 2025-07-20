package br.com.seucaio.cryptoexchanges.data.repository

import br.com.seucaio.cryptoexchanges.data.local.entity.ExchangeEntity
import br.com.seucaio.cryptoexchanges.data.mapper.ExchangeMapper
import br.com.seucaio.cryptoexchanges.data.model.ExchangeResponse
import br.com.seucaio.cryptoexchanges.data.source.ExchangeLocalDataSource
import br.com.seucaio.cryptoexchanges.data.source.ExchangeRemoteDataSource
import br.com.seucaio.cryptoexchanges.domain.model.Exchange
import br.com.seucaio.cryptoexchanges.domain.repository.ExchangeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.util.concurrent.TimeUnit

class ExchangeRepositoryImpl(
    private val localDataSource: ExchangeLocalDataSource,
    private val remoteDataSource: ExchangeRemoteDataSource,
    private val mapper: ExchangeMapper = ExchangeMapper
) : ExchangeRepository {
    private val cacheTimeout = TimeUnit.HOURS.toMillis(1)
    private val exchangesResponse: Flow<List<ExchangeResponse>> by lazy {
        remoteDataSource.getExchanges()
    }
    private val exchangesEntity: Flow<List<ExchangeEntity>> by lazy {
        localDataSource.getAllExchanges()
    }
    private val exchangesFromRemote: Flow<List<Exchange>> by lazy {
        exchangesResponse.map { it.map(mapper::mapRemoteToDomain) }
    }
    private val exchangesFromLocal: Flow<List<Exchange>> by lazy {
        exchangesEntity.map { it.map(mapper::mapLocalToDomain) }
    }


    override fun getExchanges(forceRefresh: Boolean): Flow<List<Exchange>> {
        return exchangesFromLocal.onStart {
            if (forceRefresh) {
                fetchAndCacheExchangesFromApi()
            } else {
                refreshExchangesIfNeeded()
            }
        }
    }

    private suspend fun refreshExchangesIfNeeded() {
        val oldestTimestamp = localDataSource.getOldestUpdateTimestamp()
        val currentTime = System.currentTimeMillis()

        if (oldestTimestamp == null || (currentTime - oldestTimestamp) > cacheTimeout) {
            fetchAndCacheExchangesFromApi()
        }
    }

    private suspend fun fetchAndCacheExchangesFromApi() {
        try {
            val entityList: List<ExchangeEntity> = exchangesFromRemote.map { list ->
                list.map { mapper.mapDomainToEntity(it) }
            }.first()

            localDataSource.deleteAllExchanges()
            localDataSource.insertAllExchanges(exchanges = entityList)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}