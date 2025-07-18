package br.com.seucaio.cryptoexchanges.data.service

import br.com.seucaio.cryptoexchanges.data.model.ExchangeResponse
import retrofit2.http.GET

interface ApiService {
    @GET("exchanges")
    suspend fun getExchanges(): List<ExchangeResponse>
}