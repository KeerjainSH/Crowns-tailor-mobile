package com.keerjain.crownstailor.data.entities.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TailorCredentials(
    val username: String?,
    val password: String?,
    val email: String?,
) : Parcelable