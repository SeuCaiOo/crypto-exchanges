package br.com.seucaio.cryptoexchanges.core.extension

fun Boolean?.orFalse() = this ?: false

fun Boolean?.orTrue() = this ?: true
