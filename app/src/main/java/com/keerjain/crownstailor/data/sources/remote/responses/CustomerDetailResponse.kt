package com.keerjain.crownstailor.data.sources.remote.responses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomerDetailResponse(
	val data: UserResponse? = null,
	val success: Boolean? = null,
	val message: String? = null
) : Parcelable

@Parcelize
data class UserResponse(
	val email: String? = null,
	val username: String? = null
) : Parcelable
