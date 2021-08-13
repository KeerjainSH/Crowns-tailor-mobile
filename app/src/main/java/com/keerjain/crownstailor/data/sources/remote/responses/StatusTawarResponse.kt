package com.keerjain.crownstailor.data.sources.remote.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.Penawaran
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatusTawarResponse(

    @field:SerializedName("data")
    val data: Penawaran? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
) : Parcelable
