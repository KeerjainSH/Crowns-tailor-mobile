package com.keerjain.crownstailor

import android.app.Application
import com.keerjain.crownstailor.utils.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    remoteDataSourceModule,
                    sessionModule,
                    repositoryModule,
                    viewModelModule,
                    retrofitModule,
                    apiModule,
                )
            )
        }
    }
}