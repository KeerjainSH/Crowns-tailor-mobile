package com.keerjain.crownstailor.data.sources.remote.posts

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class IsiHargaPost (
    @SerializedName("id_pesanan")
    @Expose
    val idPesanan: Int,

    @SerializedName("biaya_jahit")
    @Expose
    val biayaJahit: Int,

    @SerializedName("biaya_material")
    @Expose
    val biayaMaterial: Int,

    @SerializedName("biaya_kirim")
    @Expose
    val biayaKirim: Int,

    @SerializedName("biaya_jemput")
    @Expose
    val biayaJemput: Int,
) : Parcelable