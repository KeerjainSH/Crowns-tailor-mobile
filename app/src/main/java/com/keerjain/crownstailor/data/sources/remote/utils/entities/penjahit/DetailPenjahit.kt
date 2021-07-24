package com.keerjain.crownstailor.data.sources.remote.utils.entities.penjahit

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailPenjahit(

    @field:SerializedName("provinsi")
    val provinsi: String? = null,

    @field:SerializedName("nama_rek")
    val namaRek: String? = null,

    @field:SerializedName("kota")
    val kota: String? = null,

    @field:SerializedName("no_hp")
    val noHp: String? = null,

    @field:SerializedName("no_rekening")
    val noRekening: String? = null,

    @field:SerializedName("id_user")
    val idUser: Int? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null,

    @field:SerializedName("katalog")
    val katalog: List<KatalogItem?>? = null,

    @field:SerializedName("bank")
    val bank: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("kecamatan")
    val kecamatan: String? = null,

    @field:SerializedName("kodepos")
    val kodepos: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("jenis_kelamin")
    val jenisKelamin: String? = null,

    @field:SerializedName("tanggal_lahir")
    val tanggalLahir: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null
) : Parcelable
