package com.keerjain.crownstailor.viewmodels

import androidx.lifecycle.ViewModel
import com.keerjain.crownstailor.data.AppRepository
import com.keerjain.crownstailor.data.entities.detail.TailorCredentials

class RegisterViewModel(private val repos: AppRepository) : ViewModel() {
    fun register(tailor: TailorCredentials) = repos.register(tailor)
}