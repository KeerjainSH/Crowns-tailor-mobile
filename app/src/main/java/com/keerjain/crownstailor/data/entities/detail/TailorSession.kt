package com.keerjain.crownstailor.data.entities.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TailorSession(
    var username: String,
    var name: String,
    var userId: Int?,
    var token: String?,
) : Parcelable
