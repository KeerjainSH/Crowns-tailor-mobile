package com.keerjain.crownstailor.viewmodels

import androidx.lifecycle.ViewModel
import com.keerjain.crownstailor.data.AppRepository
import com.keerjain.crownstailor.data.sources.remote.posts.ProfileUpdatePost

class SettingViewModel(private val repos: AppRepository) : ViewModel() {

    fun getProductList() = repos.getCatalog()

    fun getProfile() = repos.getTailorDetails()

    fun updateProfile(profile: ProfileUpdatePost) = repos.updateTailorDetails(profile)
}