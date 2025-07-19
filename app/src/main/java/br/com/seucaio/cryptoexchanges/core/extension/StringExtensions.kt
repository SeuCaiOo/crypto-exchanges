package br.com.seucaio.cryptoexchanges.core.extension

import br.com.seucaio.cryptoexchanges.core.EMPTY_STRING

fun String?.orDefault(default: String) = this ?: default

fun String?.orEmpty() = this ?: EMPTY_STRING
