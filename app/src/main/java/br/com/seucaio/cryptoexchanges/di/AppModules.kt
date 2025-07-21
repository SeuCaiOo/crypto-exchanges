package br.com.seucaio.cryptoexchanges.di

import br.com.seucaio.cryptoexchanges.core.utils.network.ConnectivityChecker
import br.com.seucaio.cryptoexchanges.data.local.dao.ExchangeDao
import br.com.seucaio.cryptoexchanges.data.local.database.MyAppDatabase
import br.com.seucaio.cryptoexchanges.data.repository.ExchangeRepositoryImpl
import br.com.seucaio.cryptoexchanges.data.service.ApiService
import br.com.seucaio.cryptoexchanges.data.source.ExchangeLocalDataSource
import br.com.seucaio.cryptoexchanges.data.source.ExchangeRemoteDataSource
import br.com.seucaio.cryptoexchanges.domain.repository.ExchangeRepository
import br.com.seucaio.cryptoexchanges.domain.usecase.GetExchangesUseCase
import br.com.seucaio.cryptoexchanges.ui.screen.ExchangeViewModel
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val localModule = module {
    single<MyAppDatabase> { MyAppDatabase.getDatabase(context = get()) }
    single<ExchangeDao> { get<MyAppDatabase>().exchangeDao() }
    single<ExchangeLocalDataSource> { ExchangeLocalDataSource(exchangeDao = get<ExchangeDao>()) }
}

val remoteModule = module {
    single { ConnectivityChecker(androidApplication()) }
    single<HttpLoggingInterceptor> { NetworkProvider.loggingInterceptor() }
    single<NetworkProvider.ApiKeyInterceptor> { NetworkProvider.apiKeyInterceptor() }
    single<ChuckerInterceptor> { NetworkProvider.chuckerInterceptor(context = get()) }
    single<OkHttpClient> {
        NetworkProvider.okHttpClient(
            interceptors = listOf(
                get<HttpLoggingInterceptor>(),
                get<NetworkProvider.ApiKeyInterceptor>(),
                get<ChuckerInterceptor>()
            )
        )
    }
    single<Retrofit> { NetworkProvider.providesRetrofit(okHttpClient = get<OkHttpClient>()) }
    single<ApiService> { get<Retrofit>().create(ApiService::class.java) }
}

val dataModule = module {
    single<ExchangeRemoteDataSource> {
        ExchangeRemoteDataSource(
            apiService = get<ApiService>(),
            connectivityChecker = get<ConnectivityChecker>()
        )
    }
    single<ExchangeRepository> {
        ExchangeRepositoryImpl(
            localDataSource = get<ExchangeLocalDataSource>(),
            remoteDataSource = get<ExchangeRemoteDataSource>()
        )
    }
}

val useCaseModule = module {
    single<GetExchangesUseCase> { GetExchangesUseCase(repository = get<ExchangeRepository>()) }
}

val viewModelModule = module {
    viewModel<ExchangeViewModel> {
        ExchangeViewModel(getExchangesUseCase = get<GetExchangesUseCase>())
    }
}

val appModules = listOf(localModule, remoteModule, dataModule, useCaseModule, viewModelModule)
