package com.keerjain.crownstailor.data.sources.remote.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.Pembayaran
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.Penawaran
import kotlinx.parcelize.Parcelize

@Parcelize
data class IsiHargaResponse(

    @field:SerializedName("data")
    val data: ReturnHarga? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
) : Parcelable

@Parcelize
data class ReturnHarga(

    @field:SerializedName("pembayaran")
    val pembayaran: Pembayaran? = null,

    @field:SerializedName("penawaran")
    val penawaran: Penawaran? = null
) : Parcelable
