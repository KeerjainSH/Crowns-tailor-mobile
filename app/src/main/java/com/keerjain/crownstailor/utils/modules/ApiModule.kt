package com.keerjain.crownstailor.utils.modules

import com.keerjain.crownstailor.data.sources.remote.api.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun provideUseApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    single { provideUseApi(get()) }
}