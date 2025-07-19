package br.com.seucaio.cryptoexchanges.core.utils

import android.icu.text.NumberFormat
import br.com.seucaio.cryptoexchanges.core.extension.ZERO
import java.util.Locale
import kotlin.math.abs

private val BASE_USD_FORMATTER: NumberFormat by lazy {
    NumberFormat.getCurrencyInstance(Locale.US)
}

private data class Magnitude(val value: Double, val symbol: String)

private val MAGNITUDES = listOf(
    Magnitude(value = 1_000_000_000_000.0, symbol = "T"),
    Magnitude(value = 1_000_000_000.0, symbol = "B"),
    Magnitude(value = 1_000_000.0, symbol = "M"),
    Magnitude(value = 1_000.0, symbol = "K")
)

fun Double.asFullValue(): String = toFormattedString(valueToFormat = this, fullValue = true)

fun Double.asAbbreviationValue(): String = toFormattedString(valueToFormat = this)

fun toFormattedString(valueToFormat: Double, fullValue: Boolean = false): String {
    if (valueToFormat.isNaN() || valueToFormat.isInfinite()) {
        return BASE_USD_FORMATTER.format(Double.ZERO)
    }

    if (!fullValue) {
        for (magnitude in MAGNITUDES.sortedByDescending { it.value }) {
            if (abs(valueToFormat) >= magnitude.value) {
                val scaledValue = valueToFormat / magnitude.value
                return "${BASE_USD_FORMATTER.format(scaledValue)}${magnitude.symbol}"
            }
        }
    }

    return BASE_USD_FORMATTER.format(valueToFormat)
}