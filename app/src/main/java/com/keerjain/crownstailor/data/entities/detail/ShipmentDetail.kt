package com.keerjain.crownstailor.data.entities.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShipmentDetail(
    val receiverName: String,
    val receiverAddress: String,
    val type: Int,
) : Parcelable
