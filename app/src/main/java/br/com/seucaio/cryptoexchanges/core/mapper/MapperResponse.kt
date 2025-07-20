package br.com.seucaio.cryptoexchanges.core.mapper

interface MapperResponse<S, T> {
    fun map(source: S): T
}

interface MapperEntity<S, T> {
    fun mapToEntity(source: S): T
    fun mapToDomain(source: T): S
}