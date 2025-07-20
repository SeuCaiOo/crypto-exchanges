package br.com.seucaio.cryptoexchanges.core.mapper

interface MapperResponse<S, T> {
    fun map(source: S): T
}

interface MapperEntity<S, T> {
    fun mapToEntity(source: S): T
    fun mapToDomain(source: T): S
}

interface Mapper<R, L, T> {
    fun mapLocalToDomain(source: L) : T
    fun mapRemoteToDomain(source: R): T
    fun mapRemoteToEntity(source: R) : L
    fun mapDomainToEntity(source: T) : L
}