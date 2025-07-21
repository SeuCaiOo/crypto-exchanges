package br.com.seucaio.cryptoexchanges.core.extension

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BooleanExtensionsTest {

    @Test
    fun `orFalse should return false when boolean is null`() {
        val boolean: Boolean? = null
        assertFalse(boolean.orFalse())
    }

    @Test
    fun `orFalse should return false when boolean is false`() {
        val boolean: Boolean? = false
        assertFalse(boolean.orFalse())
    }

    @Test
    fun `orFalse should return true when boolean is true`() {
        val boolean: Boolean? = true
        assertTrue(boolean.orFalse())
    }

    @Test
    fun `orTrue should return true when boolean is null`() {
        val boolean: Boolean? = null
        assertTrue(boolean.orTrue())
    }

    @Test
    fun `orTrue should return true when boolean is true`() {
        val boolean: Boolean? = true
        assertTrue(boolean.orTrue())
    }

    @Test
    fun `orTrue should return false when boolean is false`() {
        val boolean: Boolean? = false
        assertFalse(boolean.orTrue())
    }
}