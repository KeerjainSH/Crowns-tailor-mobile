package com.keerjain.crownstailor.data.sources.remote.posts

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginPost(
    @SerializedName("username")
    @Expose
    val username: String?,

    @SerializedName("password")
    @Expose
    val password: String?,
) : Parcelable
