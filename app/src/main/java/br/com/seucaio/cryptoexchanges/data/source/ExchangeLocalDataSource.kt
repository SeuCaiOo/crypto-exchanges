package br.com.seucaio.cryptoexchanges.data.source

import br.com.seucaio.cryptoexchanges.data.local.dao.ExchangeDao
import br.com.seucaio.cryptoexchanges.data.local.entity.ExchangeEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class ExchangeLocalDataSource(
    private val exchangeDao: ExchangeDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getAllExchanges(): Flow<List<ExchangeEntity>> {
        return exchangeDao.getAllExchanges()
            .catch { throw it }
            .flowOn(ioDispatcher)
    }

    suspend fun insertAllExchanges(exchanges: List<ExchangeEntity>)  {
        withContext(ioDispatcher) { exchangeDao.insertAllExchanges(exchanges) }
    }

    suspend fun deleteAllExchanges() {
        withContext(ioDispatcher) { exchangeDao.deleteAllExchanges() }
    }

    suspend fun getOldestUpdateTimestamp(): Long? {
        return exchangeDao.getOldestUpdateTimestamp()
    }
}