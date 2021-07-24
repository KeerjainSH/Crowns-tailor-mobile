package com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DesignKustom(

    @field:SerializedName("id_pesanan")
    val idPesanan: Int? = null,

    @field:SerializedName("foto")
    val foto: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null
) : Parcelable
