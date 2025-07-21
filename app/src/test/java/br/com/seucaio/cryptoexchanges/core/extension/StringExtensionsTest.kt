package br.com.seucaio.cryptoexchanges.core.extension

import org.junit.Assert.assertEquals
import org.junit.Test

class StringExtensionsTest {

    @Test
    fun `EMPTY should return an empty string`() {
        assertEquals("", String.EMPTY)
    }

    @Test
    fun `orDefault should return default value when String is null`() {
        val string: String? = null
        assertEquals("default", string.orDefault("default"))
    }

    @Test
    fun `orDefault should return its own value when String is not null`() {
        val string: String? = "test"
        assertEquals("test", string.orDefault("default"))
    }
}