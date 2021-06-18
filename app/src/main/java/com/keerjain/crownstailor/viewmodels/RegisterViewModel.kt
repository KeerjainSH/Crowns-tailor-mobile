package com.keerjain.crownstailor.viewmodels

import androidx.lifecycle.ViewModel
import com.keerjain.crownstailor.data.AppRepository
import com.keerjain.crownstailor.data.entities.UserCredentials

class RegisterViewModel(private val repos: AppRepository) : ViewModel() {
    fun register(user: UserCredentials) = repos.register(user)
}