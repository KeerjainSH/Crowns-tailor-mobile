package com.keerjain.crownstailor.data.sources.remote.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomerDetailResponse(
	@SerializedName("data")
	val data: UserResponse? = null,

	@SerializedName("success")
	val success: Boolean? = null,

	@SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class UserResponse(
	@SerializedName("email")
	val email: String? = null,

	@SerializedName("username")
	val username: String? = null,

	@SerializedName("nama")
	val name: String? = null,
) : Parcelable
