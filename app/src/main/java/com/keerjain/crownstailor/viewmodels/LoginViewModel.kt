package com.keerjain.crownstailor.viewmodels

import androidx.lifecycle.ViewModel
import com.keerjain.crownstailor.data.AppRepository
import com.keerjain.crownstailor.data.entities.detail.TailorCredentials

class LoginViewModel(private val repos: AppRepository) : ViewModel() {
    fun signIn(tailor: TailorCredentials) = repos.signIn(tailor)
}