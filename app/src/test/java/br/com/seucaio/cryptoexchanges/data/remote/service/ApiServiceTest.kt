package br.com.seucaio.cryptoexchanges.data.remote.service

import com.google.common.truth.Truth.assertThat
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import java.net.HttpURLConnection

class ApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: ApiService

    @OptIn(ExperimentalSerializationApi::class)
    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getExchanges should return a list of exchanges on success`() = runBlocking {
        // Gemini: Mock response for successful API call
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("""
                [
                    {
                        "exchange_id": "BINANCE",
                        "website": "https://www.binance.com/",
                        "name": "Binance"
                    }
                ]
            """)

        mockWebServer.enqueue(mockResponse)

        val exchanges = service.getExchanges()

        val request = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo("/exchanges")
        assertThat(exchanges).isNotNull()
        assertThat(exchanges).isNotEmpty()
        assertThat(exchanges[0].exchangeId).isEqualTo("BINANCE")
        assertThat(exchanges[0].name).isEqualTo("Binance")
    }

    @Test
    fun `getExchanges should throw an exception on API error`() = runBlocking {
        // Gemini: Mock response for API error
        val errorResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
            .setBody("""
                {
                    "error": "Internal Server Error"
                }
            """)

        mockWebServer.enqueue(errorResponse)

        var exception: Exception? = null
        try {
            service.getExchanges()
        } catch (e: Exception) {
            exception = e
        }

        val request = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo("/exchanges")
        assertThat(exception).isNotNull()
    }
}
