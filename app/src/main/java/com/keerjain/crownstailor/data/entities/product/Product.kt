package com.keerjain.crownstailor.data.entities.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val productName: String,
    var isChecked: Boolean? = false,
) : Parcelable
