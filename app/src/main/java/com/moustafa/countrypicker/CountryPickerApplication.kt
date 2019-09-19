package com.moustafa.countrypicker

import android.app.Application
import com.moustafa.countrypicker.di.repositoryModule
import com.moustafa.countrypicker.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * @author moustafasamhoury
 * created on Thursday, 19 Sep, 2019
 */

class CountryPickerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CountryPickerApplication)

            if (BuildConfig.DEBUG) {
                androidLogger()
            }
            androidFileProperties()

            modules(listOf(repositoryModule, viewModelsModule))
        }
    }
}
