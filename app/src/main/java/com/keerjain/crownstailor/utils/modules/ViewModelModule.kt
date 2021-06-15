package com.keerjain.crownstailor.utils.modules

import com.keerjain.crownstailor.viewmodels.LoginViewModel
import com.keerjain.crownstailor.viewmodels.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        LoginViewModel(get())
    }

    viewModel {
        RegisterViewModel(get())
    }
}