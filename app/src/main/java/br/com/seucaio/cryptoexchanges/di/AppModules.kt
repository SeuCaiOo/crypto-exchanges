package br.com.seucaio.cryptoexchanges.di

import br.com.seucaio.cryptoexchanges.data.repository.ExchangeRepositoryImpl
import br.com.seucaio.cryptoexchanges.data.service.ApiService
import br.com.seucaio.cryptoexchanges.data.source.ExchangeDataSource
import br.com.seucaio.cryptoexchanges.domain.repository.ExchangeRepository
import br.com.seucaio.cryptoexchanges.domain.usecase.GetExchangesUseCase
import br.com.seucaio.cryptoexchanges.ui.screen.ExchangeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single<HttpLoggingInterceptor> { NetworkProvider.loggingInterceptor() }
    single<OkHttpClient> {
        NetworkProvider.okHttpClient(interceptors = listOf(get<HttpLoggingInterceptor>()))
    }
    single<Retrofit> { NetworkProvider.providesRetrofit(okHttpClient = get<OkHttpClient>()) }
    single<ApiService> { get<Retrofit>().create(ApiService::class.java) }
}

val dataModule = module {
    single<ExchangeDataSource> { ExchangeDataSource(apiService = get<ApiService>()) }
    single<ExchangeRepository> { ExchangeRepositoryImpl(dataSource = get<ExchangeDataSource>()) }
}

val useCaseModule = module {
    single<GetExchangesUseCase> { GetExchangesUseCase(repository = get<ExchangeRepository>()) }
}

val viewModelModule = module {
    viewModel<ExchangeViewModel> {
        ExchangeViewModel(getExchangesUseCase = get<GetExchangesUseCase>())
    }
}

val appModules = listOf(networkModule, dataModule, useCaseModule, viewModelModule)
