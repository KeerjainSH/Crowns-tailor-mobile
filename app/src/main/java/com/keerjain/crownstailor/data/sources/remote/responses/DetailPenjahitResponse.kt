package com.keerjain.crownstailor.data.sources.remote.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.keerjain.crownstailor.data.sources.remote.utils.entities.penjahit.DetailPenjahit
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailPenjahitResponse(

    @field:SerializedName("data")
    val data: DetailPenjahit? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
) : Parcelable
