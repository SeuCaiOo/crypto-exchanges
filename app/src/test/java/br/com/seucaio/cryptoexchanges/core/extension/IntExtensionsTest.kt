package br.com.seucaio.cryptoexchanges.core.extension

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class IntExtensionsTest {

    @Test
    fun `defaultValue should return default value when Int is null`() {
        val int: Int? = null
        assertEquals(0, int.defaultValue())
        assertEquals(10, int.defaultValue(10))
    }

    @Test
    fun `defaultValue should return its own value when Int is not null`() {
        val int: Int? = 5
        assertEquals(5, int.defaultValue())
        assertEquals(5, int.defaultValue(10))
    }

    @Test
    fun `isZero should return true when Int is zero`() {
        val int = 0
        assertTrue(int.isZero())
    }

    @Test
    fun `isZero should return false when Int is not zero`() {
        val int = 1
        assertFalse(int.isZero())
    }

    @Test
    fun `orZero should return zero when Int is null`() {
        val int: Int? = null
        assertEquals(0, int.orZero())
    }

    @Test
    fun `orZero should return its own value when Int is not null`() {
        val int: Int? = 7
        assertEquals(7, int.orZero())
    }

    @Test
    fun `toStringSafe should return default string value when Int is null`() {
        val int: Int? = null
        assertEquals("0", int.toStringSafe())
    }

    @Test
    fun `toStringSafe should return string of its own value when Int is not null`() {
        val int: Int? = 123
        assertEquals("123", int.toStringSafe())
    }
}