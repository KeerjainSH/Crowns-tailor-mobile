package com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailJahit(

    @field:SerializedName("lengan")
    val lengan: Double? = null,

    @field:SerializedName("id_pesanan")
    val idPesanan: Int? = null,

    @field:SerializedName("dada")
    val dada: Double? = null,

    @field:SerializedName("tinggi_tubuh")
    val tinggiTubuh: Double? = null,

    @field:SerializedName("berat_badan")
    val beratBadan: Double? = null,

    @field:SerializedName("instruksi_pembuatan")
    val instruksiPembuatan: String? = null,

    @field:SerializedName("pinggang")
    val pinggang: Double? = null,

    @field:SerializedName("nama_lengkap")
    val namaLengkap: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("leher")
    val leher: Double? = null
) : Parcelable
