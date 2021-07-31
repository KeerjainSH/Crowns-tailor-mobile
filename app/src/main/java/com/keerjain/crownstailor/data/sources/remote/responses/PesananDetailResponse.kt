package com.keerjain.crownstailor.data.sources.remote.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.DetailPesanan
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.PesananBaju
import kotlinx.parcelize.Parcelize

@Parcelize
data class PesananDetailResponse(
	@SerializedName("data")
	val data: PesananBaju? = null,

	@SerializedName("success")
	val success: Boolean? = null,

	@SerializedName("message")
	val message: String? = null
) : Parcelable