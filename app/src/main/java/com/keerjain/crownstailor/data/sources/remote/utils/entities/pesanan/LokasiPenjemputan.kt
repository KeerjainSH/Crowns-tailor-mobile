package com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LokasiPenjemputan(

    @field:SerializedName("provinsi")
    val provinsi: String? = null,

    @field:SerializedName("kota")
    val kota: String? = null,

    @field:SerializedName("id_pesanan")
    val idPesanan: Int? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("instruksi")
    val instruksi: String? = null,

    @field:SerializedName("kode_pos")
    val kodePos: String? = null,

    @field:SerializedName("kecamatan")
    val kecamatan: String? = null,

    @field:SerializedName("waktu")
    val waktu: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("tipe")
    val tipe: Int? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null
) : Parcelable
