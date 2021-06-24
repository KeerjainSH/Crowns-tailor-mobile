package com.keerjain.crownstailor.data.entities.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegistrationData(

	@field:SerializedName("kota")
	var kota: String? = null,

	@field:SerializedName("no_hp")
	var noHp: String? = null,

	@field:SerializedName("list_id_baju")
	var listIdBaju: List<ListIdBajuItem?>? = null,

	@field:SerializedName("no_rekening")
	var noRekening: String? = null,

	@field:SerializedName("alamat")
	var alamat: String? = null,

	@field:SerializedName("password")
	var password: String? = null,

	@field:SerializedName("bank")
	var bank: String? = null,

	@field:SerializedName("nama")
	var nama: String? = null,

	@field:SerializedName("kecamatan")
	var kecamatan: String? = null,

	@field:SerializedName("kodepos")
	var kodepos: String? = null,

	@field:SerializedName("jenis_kelamin")
	var jenisKelamin: String? = null,

	@field:SerializedName("email")
	var email: String? = null,

	@field:SerializedName("tanggal_lahir")
	var tanggalLahir: String? = null,

	@field:SerializedName("username")
	var username: String? = null
) : Parcelable

@Parcelize
data class ListIdBajuItem(

	@field:SerializedName("id_baju")
	var idBaju: Int? = null
) : Parcelable
