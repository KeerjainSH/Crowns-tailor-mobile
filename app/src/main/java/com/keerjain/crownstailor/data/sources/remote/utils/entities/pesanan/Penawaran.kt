package com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Penawaran(

    @field:SerializedName("id_pesanan")
    val idPesanan: Int? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("hari_tawar")
    val hariTawar: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("jumlah_penawaran")
    val jumlahPenawaran: Double? = null,

    @field:SerializedName("status_penawaran")
    val statusPenawaran: String? = null
) : Parcelable
