package br.com.seucaio.cryptoexchanges.core.extension

val String.Companion.EMPTY get() = ""

fun String?.orDefault(default: String) = this ?: default
