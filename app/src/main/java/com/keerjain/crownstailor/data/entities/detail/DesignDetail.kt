package com.keerjain.crownstailor.data.entities.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DesignDetail(
    val foto: String,
    val deskripsi: String,
) : Parcelable
