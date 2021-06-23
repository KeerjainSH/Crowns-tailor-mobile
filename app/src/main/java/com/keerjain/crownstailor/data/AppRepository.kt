package com.keerjain.crownstailor.data

import com.keerjain.crownstailor.data.entities.detail.TailorCredentials

interface AppRepository {
    fun signIn(tailor: TailorCredentials): Boolean
    fun register(tailor: TailorCredentials): Boolean
}