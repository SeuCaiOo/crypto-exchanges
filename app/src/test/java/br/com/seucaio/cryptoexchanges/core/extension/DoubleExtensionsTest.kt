package br.com.seucaio.cryptoexchanges.core.extension

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DoubleExtensionsTest {

    @Test
    fun `defaultValue should return default value when Double is null`() {
        val double: Double? = null
        assertEquals(0.0, double.defaultValue(), 0.0)
        assertEquals(10.0, double.defaultValue(10.0), 0.0)
    }

    @Test
    fun `defaultValue should return its own value when Double is not null`() {
        val double: Double? = 5.5
        assertEquals(5.5, double.defaultValue(), 0.0)
        assertEquals(5.5, double.defaultValue(10.0), 0.0)
    }

    @Test
    fun `isZero should return true when Double is zero`() {
        val double = 0.0
        assertTrue(double.isZero())
    }

    @Test
    fun `isZero should return false when Double is not zero`() {
        val double = 1.0
        assertFalse(double.isZero())
    }

    @Test
    fun `orZero should return zero when Double is null`() {
        val double: Double? = null
        assertEquals(0.0, double.orZero(), 0.0)
    }

    @Test
    fun `orZero should return its own value when Double is not null`() {
        val double: Double? = 7.7
        assertEquals(7.7, double.orZero(), 0.0)
    }

    @Test
    fun `toStringSafe should return default string value when Double is null`() {
        val double: Double? = null
        assertEquals("0.0", double.toStringSafe())
    }

    @Test
    fun `toStringSafe should return string of its own value when Double is not null`() {
        val double: Double? = 12.34
        assertEquals("12.34", double.toStringSafe())
    }
}