package br.com.seucaio.cryptoexchanges.core.mapper

interface Mapper<S, T> {
    fun map(source: S): T
}