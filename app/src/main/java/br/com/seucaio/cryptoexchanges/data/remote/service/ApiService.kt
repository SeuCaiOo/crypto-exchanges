package br.com.seucaio.cryptoexchanges.data.remote.service

import br.com.seucaio.cryptoexchanges.data.remote.dto.ExchangeResponse
import retrofit2.http.GET

interface ApiService {
    @GET("exchanges")
    suspend fun getExchanges(): List<ExchangeResponse>

    companion object {
        const val BASE_URL = "https://rest.coinapi.io/v1/"
    }
}