package com.keerjain.crownstailor.data.sources

import com.keerjain.crownstailor.data.AppRepository
import com.keerjain.crownstailor.data.entities.UserCredentials
import com.keerjain.crownstailor.data.sources.remote.RemoteDataSource
import com.keerjain.crownstailor.utils.SessionManager

class AppRepositoryImpl(
    private val remote: RemoteDataSource,
    private val sessionManager: SessionManager
) : AppRepository {
    override fun signIn(user: UserCredentials): Boolean {
        sessionManager.createLoginSession(user.username.toString())
        return true
    }

    override fun register(user: UserCredentials): Boolean {
        val isRegistered = remote.registerUser(user)

        if (isRegistered) {
            sessionManager.createLoginSession(user.username.toString())
        }

        return isRegistered
    }
}