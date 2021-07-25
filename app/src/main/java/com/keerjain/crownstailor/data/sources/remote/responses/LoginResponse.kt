package com.keerjain.crownstailor.data.sources.remote.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(
	@SerializedName("data")
	val data: Data? = null,

	@SerializedName("success")
	val success: Boolean? = null,

	@SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class Data(
	@SerializedName("id_user")
	val idUser: Int? = null,

	@SerializedName("token")
	val token: String? = null
) : Parcelable
