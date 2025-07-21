package br.com.seucaio.cryptoexchanges.core.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

private const val DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy"

fun String.toFormattedDateString(
    datePattern: String = DATE_FORMAT_DD_MM_YYYY,
    targetZoneId: ZoneId = ZoneId.systemDefault()
): String? {
    val isoDateString = this
    return try {
        val instant = Instant.parse(isoDateString)
        val localDateTime = LocalDateTime.ofInstant(instant,targetZoneId)
        localDateTime.format(getDateTimeFormatter(datePattern))
    } catch (e: DateTimeParseException) {
        e.printStackTrace()
        null
    }
}

private fun getDateTimeFormatter(datePattern: String): DateTimeFormatter? {
    return DateTimeFormatter.ofPattern(datePattern, Locale.getDefault())
}