package com.keerjain.crownstailor.utils.modules

import com.keerjain.crownstailor.data.AppRepository
import com.keerjain.crownstailor.data.sources.AppRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<AppRepository> {
        AppRepositoryImpl(get(), get())
    }
}