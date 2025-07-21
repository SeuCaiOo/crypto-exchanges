package br.com.seucaio.cryptoexchanges.data.remote.service

import br.com.seucaio.cryptoexchanges.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitConfig {
    private const val JSON_MEDIA_TYPE = "application/json"

    @OptIn(ExperimentalSerializationApi::class)
    fun jsonConverterFactory(): Converter.Factory {
        val contentType = JSON_MEDIA_TYPE.toMediaType()

        val json = if (BuildConfig.DEBUG) {
            Json {
                encodeDefaults = true
                ignoreUnknownKeys = true
                isLenient = true
            }
        } else {
            Json {
                encodeDefaults = true
                ignoreUnknownKeys = true
            }
        }
        return json.asConverterFactory(contentType)
    }

    fun okHttpClient(interceptors: List<Interceptor>): OkHttpClient {
        return OkHttpClient.Builder()
            .apply { interceptors.forEach { addInterceptor(it) } }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiService.Companion.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(jsonConverterFactory())
            .build()
    }
}