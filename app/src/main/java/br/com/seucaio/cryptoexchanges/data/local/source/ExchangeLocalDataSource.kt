package br.com.seucaio.cryptoexchanges.data.local.source

import br.com.seucaio.cryptoexchanges.data.local.dao.ExchangeDao
import br.com.seucaio.cryptoexchanges.data.local.entity.ExchangeEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ExchangeLocalDataSource(
    private val exchangeDao: ExchangeDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getAllExchanges(): Flow<List<ExchangeEntity>> {
        return exchangeDao.getAllExchanges()
    }

    suspend fun clearAndCacheExchanges(exchanges: List<ExchangeEntity>) =
        withContext(ioDispatcher) {
            exchangeDao.deleteAllExchanges()
            exchangeDao.insertAllExchanges(exchanges)
        }

    suspend fun getOldestUpdateTimestamp(): Long? {
        return withContext(ioDispatcher) { exchangeDao.getOldestUpdateTimestamp() }
    }
}