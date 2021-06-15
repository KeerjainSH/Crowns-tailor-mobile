package com.keerjain.crownstailor.utils.modules

import com.keerjain.crownstailor.utils.SessionManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val sessionModule = module {
    single {
        SessionManager(androidApplication())
    }
}