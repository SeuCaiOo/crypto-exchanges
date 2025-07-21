package br.com.seucaio.cryptoexchanges.data.remote.source

import br.com.seucaio.cryptoexchanges.core.utils.network.ConnectivityChecker
import br.com.seucaio.cryptoexchanges.core.utils.network.executeNetworkRequest
import br.com.seucaio.cryptoexchanges.data.remote.dto.ExchangeResponse
import br.com.seucaio.cryptoexchanges.data.remote.service.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ExchangeRemoteDataSource(
    private val apiService: ApiService,
    private val connectivityChecker: ConnectivityChecker,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    fun getExchanges(): Flow<List<ExchangeResponse>> {
        return flow {
            emit(connectivityChecker.executeNetworkRequest { apiService.getExchanges() })
        }
            .catch { throw it }
            .flowOn(ioDispatcher)
    }
}