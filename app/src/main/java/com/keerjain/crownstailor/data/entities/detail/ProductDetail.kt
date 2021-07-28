package com.keerjain.crownstailor.data.entities.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDetail(
    val productId: Long,
    val productName: String,
    val productPhoto: String,
    val productDescription: String?,
) : Parcelable
