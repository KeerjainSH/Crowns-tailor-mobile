package com.keerjain.crownstailor.utils.modules

import com.keerjain.crownstailor.data.sources.remote.RemoteDataSource
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single {
        RemoteDataSource(get())
    }
}