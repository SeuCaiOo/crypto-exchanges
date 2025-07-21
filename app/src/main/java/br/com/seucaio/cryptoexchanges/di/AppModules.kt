package br.com.seucaio.cryptoexchanges.di

import br.com.seucaio.cryptoexchanges.core.utils.network.ConnectivityChecker
import br.com.seucaio.cryptoexchanges.data.local.dao.ExchangeDao
import br.com.seucaio.cryptoexchanges.data.local.database.MyAppDatabase
import br.com.seucaio.cryptoexchanges.data.repository.ExchangeRepositoryImpl
import br.com.seucaio.cryptoexchanges.data.remote.service.ApiService
import br.com.seucaio.cryptoexchanges.data.local.source.ExchangeLocalDataSource
import br.com.seucaio.cryptoexchanges.data.remote.service.RetrofitConfig
import br.com.seucaio.cryptoexchanges.data.remote.service.interceptor.NetworkInterceptor
import br.com.seucaio.cryptoexchanges.data.remote.source.ExchangeRemoteDataSource
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
    single<HttpLoggingInterceptor> { NetworkInterceptor.loggingInterceptor() }
    single<NetworkInterceptor.ApiKeyInterceptor> { NetworkInterceptor.apiKeyInterceptor() }
    single<ChuckerInterceptor> { NetworkInterceptor.chuckerInterceptor(context = get()) }
    single<OkHttpClient> {
        RetrofitConfig.okHttpClient(
            interceptors = listOf(
                get<HttpLoggingInterceptor>(),
                get<NetworkInterceptor.ApiKeyInterceptor>(),
                get<ChuckerInterceptor>()
            )
        )
    }
    single<Retrofit> { RetrofitConfig.providesRetrofit(okHttpClient = get<OkHttpClient>()) }
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
