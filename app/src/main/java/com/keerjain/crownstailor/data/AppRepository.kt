package com.keerjain.crownstailor.data

import com.keerjain.crownstailor.data.entities.UserCredentials

interface AppRepository {
    fun signIn(user: UserCredentials): Boolean
    fun register(user: UserCredentials): Boolean
}