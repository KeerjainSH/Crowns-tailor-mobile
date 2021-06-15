package com.keerjain.crownstailor.data

import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun signIn(username: String, password: String): Boolean
}