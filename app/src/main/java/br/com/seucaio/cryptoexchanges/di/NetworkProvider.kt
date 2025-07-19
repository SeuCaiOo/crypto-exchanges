package br.com.seucaio.cryptoexchanges.di

import br.com.seucaio.cryptoexchanges.BuildConfig
import br.com.seucaio.cryptoexchanges.data.service.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object NetworkProvider {
    private const val JSON_MEDIA_TYPE = "application/json"

    class ApiKeyInterceptor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", BuildConfig.API_KEY)
                .build()
            return chain.proceed(newRequest)
        }
    }

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

    fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    fun okHttpClient(interceptors: List<Interceptor>): OkHttpClient {
        return OkHttpClient.Builder()
            .apply { interceptors.forEach { addInterceptor(it) } }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun apiKeyInterceptor(): ApiKeyInterceptor = ApiKeyInterceptor()

    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(jsonConverterFactory())
            .build()
    }
}
