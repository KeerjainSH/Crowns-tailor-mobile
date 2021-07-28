package com.keerjain.crownstailor.data.sources.remote.posts

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class IsiHargaPost (
    @SerializedName("id_pesanan")
    @Expose
    val idPesanan: Int?,

    @SerializedName("biaya_jahit")
    @Expose
    val biayaJahit: Float?,

    @SerializedName("biaya_material")
    @Expose
    val biayaMaterial: Float?,

    @SerializedName("biaya_kirim")
    @Expose
    val biayaKirim: Float?,

    @SerializedName("biaya_jemput")
    @Expose
    val biayaJemput: Float?,
) : Parcelable