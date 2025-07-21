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

class ExchangeRepositoryImpl(
    private val localDataSource: ExchangeLocalDataSource,
    private val remoteDataSource: ExchangeRemoteDataSource,
    private val mapper: ExchangeMapper = ExchangeMapper
) : ExchangeRepository {
    override fun getExchanges(forceRefresh: Boolean): Flow<List<Exchange>> {
        return getExchangesFromCache()
            .onStart {
                if (forceRefresh) {
                    fetchAndCacheExchangesFromApi(exchangesFromApi())
                } else {
                    refreshExchangesIfNeeded()
                }
            }
            .catch { e ->
                exchangesFromDb().first().ifEmpty { throw e }
                handleErrorRefresh(e)
            }
            .flowOn(Dispatchers.IO)
    }

    private fun handleErrorRefresh(e: Throwable) {
        val message = "Falha na atualização dos dados\n${e.message}"
        when (e) {
            is NetworkException.NoInternetException -> throw DataUpdateFailedWarning.NetworkError(message)
            is NetworkException.ApiException -> throw DataUpdateFailedWarning.ApiError(message)
            else -> throw DataUpdateFailedWarning.UnknownError(message)
        }
    }

    private fun getExchangesFromCache(): Flow<List<Exchange>> {
        return exchangesFromDb().map { it.map(mapper::mapLocalToDomain) }
    }

    private fun exchangesFromDb(): Flow<List<ExchangeEntity>> {
        return  localDataSource.getAllExchanges()
    }

    private suspend fun refreshExchangesIfNeeded() {
        val oldestTimestamp = localDataSource.getOldestUpdateTimestamp()
        if (oldestTimestamp == null) {
            fetchAndCacheExchangesFromApi(exchangesFromApi())
        }
    }

    private suspend fun exchangesFromApi(): List<ExchangeResponse> = try {
        remoteDataSource.getExchanges().first()
    } catch (e: Exception) {
        throw e
    }

    private suspend fun fetchAndCacheExchangesFromApi(remoteResponse: List<ExchangeResponse>) {
        try {
            val domainModels = remoteResponse.map(mapper::mapRemoteToDomain)
            val entities = domainModels.map(mapper::mapDomainToEntity)
            if (entities.isNotEmpty()) localDataSource.clearAndCacheExchanges(exchanges = entities)
        } catch (e: Exception) {
            throw e
        }
    }
}