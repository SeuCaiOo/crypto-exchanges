package br.com.seucaio.cryptoexchanges.core.extension

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LongExtensionsTest {

    @Test
    fun `defaultValue should return default value when Long is null`() {
        val long: Long? = null
        assertEquals(0L, long.defaultValue())
        assertEquals(10L, long.defaultValue(10L))
    }

    @Test
    fun `defaultValue should return its own value when Long is not null`() {
        val long: Long? = 5L
        assertEquals(5L, long.defaultValue())
        assertEquals(5L, long.defaultValue(10L))
    }

    @Test
    fun `isZero should return true when Long is zero`() {
        val long = 0L
        assertTrue(long.isZero())
    }

    @Test
    fun `isZero should return false when Long is not zero`() {
        val long = 1L
        assertFalse(long.isZero())
    }

    @Test
    fun `orZero should return zero when Long is null`() {
        val long: Long? = null
        assertEquals(0L, long.orZero())
    }

    @Test
    fun `orZero should return its own value when Long is not null`() {
        val long: Long? = 7L
        assertEquals(7L, long.orZero())
    }

    @Test
    fun `toStringSafe should return default string value when Long is null`() {
        val long: Long? = null
        assertEquals("0", long.toStringSafe())
    }

    @Test
    fun `toStringSafe should return string of its own value when Long is not null`() {
        val long: Long? = 123L
        assertEquals("123", long.toStringSafe())
    }
}