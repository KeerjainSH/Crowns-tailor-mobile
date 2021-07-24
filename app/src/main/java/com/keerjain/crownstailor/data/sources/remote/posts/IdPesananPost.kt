package com.keerjain.crownstailor.data.sources.remote.posts

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class IdPesananPost(
    @SerializedName("id_pesanan")
    @Expose
    val idPesanan: Int,
) : Parcelable
