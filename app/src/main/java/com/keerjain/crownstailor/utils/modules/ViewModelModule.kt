package com.keerjain.crownstailor.utils.modules

import com.keerjain.crownstailor.viewmodels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        LoginViewModel(get())
    }

    viewModel {
        RegisterViewModel(get())
    }

    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        OfferViewModel(get())
    }

    viewModel {
        OrderViewModel(get())
    }

    viewModel {
        SettingViewModel(get())
    }

    viewModel {
        OfferDetailViewModel(get())
    }

    viewModel {
        OrderDetailViewModel(get())
    }
}