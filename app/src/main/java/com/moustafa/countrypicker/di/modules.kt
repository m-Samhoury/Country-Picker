package com.moustafa.countrypicker.di

import com.moustafa.countrypicker.BuildConfig
import com.moustafa.countrypicker.repository.Repository
import com.moustafa.countrypicker.repository.RepositoryImpl
import com.moustafa.countrypicker.repository.network.NetworkLayerFactory
import com.moustafa.countrypicker.repository.network.RestCountriesService
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * @author moustafasamhoury
 * created on Tuesday, 10 Sep, 2019
 */
val repositoryModule: Module = module {
    single { NetworkLayerFactory.makeHttpClient(androidContext()) }

    single<RestCountriesService> { NetworkLayerFactory.makeServiceFactory(get()) }
    single {
        NetworkLayerFactory
            .makeRetrofit(
                BuildConfig.BASE_API_URL,
                get()
            )
    }
    single<Repository> { RepositoryImpl(get()) }
}
