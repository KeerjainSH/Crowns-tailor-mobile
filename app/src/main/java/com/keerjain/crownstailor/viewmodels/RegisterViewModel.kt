package com.keerjain.crownstailor.viewmodels

import androidx.lifecycle.ViewModel
import com.keerjain.crownstailor.data.AppRepository
import com.keerjain.crownstailor.data.sources.remote.posts.RegistrationData

class RegisterViewModel(private val repos: AppRepository) : ViewModel() {
    fun register(registrationData: RegistrationData) = repos.register(registrationData)

    fun getProductList() = repos.getCatalog()
}