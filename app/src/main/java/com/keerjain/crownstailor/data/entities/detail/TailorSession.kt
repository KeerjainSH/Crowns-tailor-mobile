package com.keerjain.crownstailor.data.entities.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TailorSession(
    val username: String,
    val name: String,
) : Parcelable
