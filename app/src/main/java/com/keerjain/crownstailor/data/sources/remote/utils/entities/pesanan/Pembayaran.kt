package com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pembayaran(

    @field:SerializedName("metode_pembayaran")
    val metodePembayaran: String? = null,

    @field:SerializedName("id_pesanan")
    val idPesanan: Int? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("status_pembayaran")
    val statusPembayaran: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("biaya_jahit")
    val biayaJahit: Double? = null,

    @field:SerializedName("biaya_jemput")
    val biayaJemput: Double? = null,

    @field:SerializedName("biaya_material")
    val biayaMaterial: Double? = null,

    @field:SerializedName("biaya_kirim")
    val biayaKirim: Double? = null
) : Parcelable
