package com.keerjain.crownstailor.data.entities.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomerDetail(
    val userId: Long,
    val userFullName: String,
    val username: String,
    val email: String,
    val photoProfile: String,
) : Parcelable
