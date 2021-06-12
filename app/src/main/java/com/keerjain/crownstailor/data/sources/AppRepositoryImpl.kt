package com.keerjain.crownstailor.data.sources

import com.keerjain.crownstailor.data.AppRepository
import com.keerjain.crownstailor.data.sources.remote.RemoteDataSource

class AppRepositoryImpl(private val remote: RemoteDataSource) : AppRepository {
}