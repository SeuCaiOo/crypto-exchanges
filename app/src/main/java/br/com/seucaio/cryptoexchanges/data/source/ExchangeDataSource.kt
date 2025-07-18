package br.com.seucaio.cryptoexchanges.data.source

import br.com.seucaio.cryptoexchanges.data.model.ExchangeResponse
import br.com.seucaio.cryptoexchanges.data.service.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ExchangeDataSource(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getExchanges(): Flow<List<ExchangeResponse>>  {
        return flow { emit(apiService.getExchanges()) }
            .catch { throw it }
            .flowOn(ioDispatcher)
    }
}