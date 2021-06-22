package com.keerjain.crownstailor.viewmodels

import androidx.lifecycle.ViewModel
import com.keerjain.crownstailor.data.AppRepository
import com.keerjain.crownstailor.data.entities.UserCredentials

class LoginViewModel(private val repos: AppRepository) : ViewModel() {
    fun signIn(user: UserCredentials) = repos.signIn(user)
}