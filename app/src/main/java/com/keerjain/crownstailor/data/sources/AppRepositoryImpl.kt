package com.keerjain.crownstailor.data.sources

import com.keerjain.crownstailor.data.AppRepository
import com.keerjain.crownstailor.data.sources.remote.RemoteDataSource
import com.keerjain.crownstailor.utils.SessionManager

class AppRepositoryImpl(
    private val remote: RemoteDataSource,
    private val sessionManager: SessionManager
) : AppRepository {
    override fun signIn(username: String, password: String): Boolean {
        sessionManager.createLoginSession(username)
        return true
    }
}