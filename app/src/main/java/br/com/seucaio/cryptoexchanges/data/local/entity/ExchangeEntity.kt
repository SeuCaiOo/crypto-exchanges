package br.com.seucaio.cryptoexchanges.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchanges")
data class ExchangeEntity(
    @PrimaryKey
    val id: String,
    val website: String?,
    val name: String?,
    val dataStart: String?,
    val dataEnd: String?,
    val dataQuoteStart: String?,
    val dataQuoteEnd: String?,
    val dataOrderBookStart: String?,
    val dataOrderBookEnd: String?,
    val dataTradeStart: String?,
    val dataTradeEnd: String?,
    val dataTradeCount: Long?,
    val dataSymbolsCount: Long?,
    val volume1HrsUsd: Double?,
    val volume1DayUsd: Double?,
    val volume1MthUsd: Double?,
    val metricId: List<String>?,
    val rank: Int?,
    val integrationStatus: String?,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)