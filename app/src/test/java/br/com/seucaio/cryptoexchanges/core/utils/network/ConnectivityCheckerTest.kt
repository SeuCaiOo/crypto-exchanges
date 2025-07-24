package br.com.seucaio.cryptoexchanges.core.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.net.ConnectException
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class ConnectivityCheckerTest {

    private val mockContext: Context = mockk()
    private val mockConnectivityManager: ConnectivityManager = mockk()
    private val mockNetwork: Network = mockk()
    private val mockNetworkCapabilities: NetworkCapabilities = mockk(relaxed = true)
    private lateinit var connectivityChecker: ConnectivityChecker

    @Before
    fun setUp() {
        every {
            mockContext.getSystemService(Context.CONNECTIVITY_SERVICE)
        } returns mockConnectivityManager
        connectivityChecker = spyk(ConnectivityChecker(mockContext))
    }

    // region isNetworkAvailable
    @Test
    fun `should return true when network is connected via WiFi`() {
        // Given
        every { mockConnectivityManager.activeNetwork } returns mockNetwork
        every {
            mockConnectivityManager.getNetworkCapabilities(mockNetwork)
        } returns mockNetworkCapabilities
        every {
            mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } returns true

        // When
        val result = connectivityChecker.isNetworkAvailable()

        // Then
        assertTrue(result)
    }

    @Test
    fun `should return true when network is connected via Cellular`() {
        // Given
        every { mockConnectivityManager.activeNetwork } returns mockNetwork
        every {
            mockConnectivityManager.getNetworkCapabilities(mockNetwork)
        } returns mockNetworkCapabilities
        every {
            mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        } returns true

        // When
        val result = connectivityChecker.isNetworkAvailable()

        // Then
        assertTrue(result)
    }

    @Test
    fun `should return false when activeNetwork is null`() {
        // Given
        every { mockConnectivityManager.activeNetwork } returns null

        // When
        val result = connectivityChecker.isNetworkAvailable()

        // Then
        assertFalse(result)
    }

    @Test
    fun `should return false when networkCapabilities is null`() {
        // Given
        every { mockConnectivityManager.activeNetwork } returns mockNetwork
        every { mockConnectivityManager.getNetworkCapabilities(mockNetwork) } returns null

        // When
        val result = connectivityChecker.isNetworkAvailable()

        // Then
        assertFalse(result)
    }

    @Test
    fun `should return false for unsupported transport type`() {
        // Given
        every { mockConnectivityManager.activeNetwork } returns mockNetwork
        every {
            mockConnectivityManager.getNetworkCapabilities(mockNetwork)
        } returns mockNetworkCapabilities

        // When
        val result = connectivityChecker.isNetworkAvailable()

        // Then
        assertFalse(result)
    }
    // endregion

    // region executeNetworkRequest
    @Test
    fun `should return result when network is available and action succeeds`() = runTest {
        // Given
        val expectedResult = "Success"
        every { connectivityChecker.isNetworkAvailable() } returns true

        // When
        val result = connectivityChecker.executeNetworkRequest { expectedResult }

        // Then
        assertEquals(expectedResult, result)
    }

    @Test
    fun `should throw NoInternetException when network is unavailable`() = runTest {
        // Given
        every { connectivityChecker.isNetworkAvailable() } returns false

        // When
        val exception = assertFails { connectivityChecker.executeNetworkRequest { } }

        // Then
        assertIs<NetworkException.NoInternetException>(exception)
    }

    @Test
    fun `should throw specific NetworkException when action throws it`() = runTest {
        // Given
        val expectedException = ConnectException()
        every { connectivityChecker.isNetworkAvailable() } returns true

        // When
        val exception = assertFails {
            connectivityChecker.executeNetworkRequest { throw expectedException }
        }

        // Then
        assertIs<NetworkException.ConnectionException>(exception)
        assertEquals(expectedException, exception.cause)
    }
    // endregion
}