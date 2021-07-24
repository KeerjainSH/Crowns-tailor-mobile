package com.keerjain.crownstailor.data.sources.remote.utils.entities.penjahit

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class KatalogItem(

    @field:SerializedName("id_kategori")
    val idKategori: Int? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("foto")
    val foto: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("memiliki_table")
    val memilikiTable: MemilikiTable? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null,

    @field:SerializedName("jenis_kelamin")
    val jenisKelamin: String? = null
) : Parcelable

@Parcelize
data class MemilikiTable(

    @field:SerializedName("id_penjahit")
    val idPenjahit: Int? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("id_baju")
    val idBaju: Int? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null
) : Parcelable