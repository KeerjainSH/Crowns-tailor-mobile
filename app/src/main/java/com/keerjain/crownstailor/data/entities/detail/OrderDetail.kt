package com.keerjain.crownstailor.data.entities.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderDetail(
    val armSize: Float,
    val waistSize: Float,
    val chestSize: Float,
    val neckSize: Float,
    val bodyHeight: Float,
    val bodyWeight: Float,
    val instructions: String,
    val design: String?,
) : Parcelable
