package com.keerjain.crownstailor.data.sources.remote

import com.keerjain.crownstailor.data.entities.detail.TailorCredentials
import com.keerjain.crownstailor.data.sources.remote.api.ApiService

class RemoteDataSource(private val api: ApiService) {
    fun registerUser(tailor: TailorCredentials): Boolean {
        return true
    }
}