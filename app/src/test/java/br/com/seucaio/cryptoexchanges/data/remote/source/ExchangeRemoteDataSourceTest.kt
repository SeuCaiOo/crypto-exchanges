package br.com.seucaio.cryptoexchanges.data.remote.source

import br.com.seucaio.cryptoexchanges.core.utils.network.ConnectivityChecker
import br.com.seucaio.cryptoexchanges.core.utils.network.NetworkException
import br.com.seucaio.cryptoexchanges.data.remote.dto.ExchangeResponse
import br.com.seucaio.cryptoexchanges.data.remote.service.ApiService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.net.SocketTimeoutException
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class ExchangeRemoteDataSourceTest {
    private val apiService: ApiService = mockk()
    private val connectivityChecker: ConnectivityChecker = mockk()
    private lateinit var dataSource: ExchangeRemoteDataSource

    @Before
    fun setUp() {
        dataSource = ExchangeRemoteDataSource(
            apiService = apiService,
            connectivityChecker = connectivityChecker,
            ioDispatcher = UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `should emit list of exchanges on successful API call`() = runTest {
        // Given
        val expectedExchanges = listOf(
            mockk<ExchangeResponse>(), mockk<ExchangeResponse>()
        )
        every { connectivityChecker.isNetworkAvailable() } returns true
        coEvery { apiService.getExchanges() } returns expectedExchanges

        // When
        val result = dataSource.getExchanges().first()

        // Then
        assertEquals(expected = expectedExchanges, actual = result)
    }

    @Test
    fun `should emit NoInternetException when network is unavailable`() = runTest {
        // Given
        every { connectivityChecker.isNetworkAvailable() } returns false

        // When
        val exception = assertFails { dataSource.getExchanges().first() }

        // Then
        assertIs<NetworkException.NoInternetException>(exception)
    }
}
