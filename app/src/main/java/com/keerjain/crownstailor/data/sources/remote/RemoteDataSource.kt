package com.keerjain.crownstailor.data.sources.remote

import com.keerjain.crownstailor.data.entities.UserCredentials
import com.keerjain.crownstailor.data.sources.remote.api.ApiService

class RemoteDataSource(private val api : ApiService) {
    fun registerUser(user: UserCredentials): Boolean {
        return true
    }
}