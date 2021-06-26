package com.keerjain.crownstailor.viewmodels

import androidx.lifecycle.ViewModel
import com.keerjain.crownstailor.data.AppRepository
import com.keerjain.crownstailor.data.entities.detail.TailorCredentials
import com.keerjain.crownstailor.data.entities.post.RegistrationData

class RegisterViewModel(private val repos: AppRepository) : ViewModel() {
    fun register(registrationData: RegistrationData) = repos.register(registrationData)
}