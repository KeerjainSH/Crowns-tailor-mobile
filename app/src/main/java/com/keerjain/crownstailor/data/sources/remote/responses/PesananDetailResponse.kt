package com.keerjain.crownstailor.data.sources.remote.responses

import android.os.Parcelable
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.DetailPesanan
import kotlinx.parcelize.Parcelize

@Parcelize
data class PesananDetailResponse(
	val data: DetailPesanan? = null,
	val success: Boolean? = null,
	val message: String? = null
) : Parcelable