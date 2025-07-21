package br.com.seucaio.cryptoexchanges.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.seucaio.cryptoexchanges.data.local.entity.ExchangeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllExchanges(exchanges: List<ExchangeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExchange(exchange: ExchangeEntity)

    @Query("SELECT * FROM exchanges")
    fun getAllExchanges(): Flow<List<ExchangeEntity>>

    @Query("SELECT * FROM exchanges WHERE id = :id")
    fun getExchangeById(id: String): Flow<ExchangeEntity?>

    @Query("DELETE FROM exchanges")
    suspend fun deleteAllExchanges()

    @Query("SELECT created_at FROM exchanges ORDER BY created_at ASC LIMIT 1")
    suspend fun getOldestUpdateTimestamp(): Long?
}