package br.com.seucaio.cryptoexchanges.data.mapper

import br.com.seucaio.cryptoexchanges.core.mapper.Mapper
import br.com.seucaio.cryptoexchanges.data.local.entity.ExchangeEntity
import br.com.seucaio.cryptoexchanges.data.remote.dto.ExchangeResponse
import br.com.seucaio.cryptoexchanges.domain.model.Exchange

object ExchangeMapper : Mapper<ExchangeResponse, ExchangeEntity, Exchange> {
    override fun mapLocalToDomain(source: ExchangeEntity): Exchange {
       return Exchange(
            exchangeId = source.id,
            website = source.website,
            name = source.name,
            dataStart = source.dataStart,
            dataEnd = source.dataEnd,
            dataQuoteStart = source.dataQuoteStart,
            dataQuoteEnd = source.dataQuoteEnd,
            dataOrderBookStart = source.dataOrderBookStart,
            dataOrderBookEnd = source.dataOrderBookEnd,
            dataTradeStart = source.dataTradeStart,
            dataTradeEnd = source.dataTradeEnd,
            dataTradeCount = source.dataTradeCount,
            dataSymbolsCount = source.dataSymbolsCount,
            volume1HrsUsd = source.volume1HrsUsd,
            volume1DayUsd = source.volume1DayUsd,
            volume1MthUsd = source.volume1MthUsd,
            metricId = source.metricId,
            rank = source.rank,
            integrationStatus = source.integrationStatus
        )
    }

    override fun mapRemoteToDomain(source: ExchangeResponse): Exchange {
       return Exchange(
            exchangeId = source.exchangeId,
            website = source.website,
            name = source.name,
            dataStart = source.dataStart,
            dataEnd = source.dataEnd,
            dataQuoteStart = source.dataQuoteStart,
            dataQuoteEnd = source.dataQuoteEnd,
            dataOrderBookStart = source.dataOrderBookStart,
            dataOrderBookEnd = source.dataOrderBookEnd,
            dataTradeStart = source.dataTradeStart,
            dataTradeEnd = source.dataTradeEnd,
            dataTradeCount = source.dataTradeCount,
            dataSymbolsCount = source.dataSymbolsCount,
            volume1HrsUsd = source.volume1HrsUsd,
            volume1DayUsd = source.volume1DayUsd,
            volume1MthUsd = source.volume1MthUsd,
            metricId = source.metricId,
            rank = source.rank,
            icons = source.mapperIcons(),
            integrationStatus = source.integrationStatus
        )
    }

    private fun ExchangeResponse.mapperIcons(): List<Exchange.Icon>? {
        return this.icons?.map {
            Exchange.Icon(exchangeId = it.exchangeId, assetId = it.assetId, url = it.url)
        }
    }

    override fun mapRemoteToEntity(source: ExchangeResponse): ExchangeEntity {
        return ExchangeEntity(
            id = source.exchangeId.orEmpty(),
            website = source.website,
            name = source.name,
            dataStart = source.dataStart,
            dataEnd = source.dataEnd,
            dataQuoteStart = source.dataQuoteStart,
            dataQuoteEnd = source.dataQuoteEnd,
            dataOrderBookStart = source.dataOrderBookStart,
            dataOrderBookEnd = source.dataOrderBookEnd,
            dataTradeStart = source.dataTradeStart,
            dataTradeEnd = source.dataTradeEnd,
            dataTradeCount = source.dataTradeCount,
            dataSymbolsCount = source.dataSymbolsCount,
            volume1HrsUsd = source.volume1HrsUsd,
            volume1DayUsd = source.volume1DayUsd,
            volume1MthUsd = source.volume1MthUsd,
            metricId = source.metricId,
            rank = source.rank,
            integrationStatus = source.integrationStatus,
            createdAt = System.currentTimeMillis()
        )
    }

    override fun mapDomainToEntity(source: Exchange): ExchangeEntity {
       return ExchangeEntity(
            id = source.exchangeId.orEmpty(),
            website = source.website,
            name = source.name,
            dataStart = source.dataStart,
            dataEnd = source.dataEnd,
            dataQuoteStart = source.dataQuoteStart,
            dataQuoteEnd = source.dataQuoteEnd,
            dataOrderBookStart = source.dataOrderBookStart,
            dataOrderBookEnd = source.dataOrderBookEnd,
            dataTradeStart = source.dataTradeStart,
            dataTradeEnd = source.dataTradeEnd,
            dataTradeCount = source.dataTradeCount,
            dataSymbolsCount = source.dataSymbolsCount,
            volume1HrsUsd = source.volume1HrsUsd,
            volume1DayUsd = source.volume1DayUsd,
            volume1MthUsd = source.volume1MthUsd,
            metricId = source.metricId,
            rank = source.rank,
            integrationStatus = source.integrationStatus,
            createdAt = System.currentTimeMillis()
        )
    }

}