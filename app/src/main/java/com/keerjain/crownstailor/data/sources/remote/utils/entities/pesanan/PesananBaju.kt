package com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PesananBaju(

    @field:SerializedName("pembayaran")
    val pembayaran: Pembayaran? = null,

    @field:SerializedName("baju")
    val baju: Baju? = null,

    @field:SerializedName("rating")
    val rating: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("status_pesanan")
    val statusPesanan: String? = null,

    @field:SerializedName("lokasi_penjemputan")
    val lokasiPenjemputan: List<LokasiPenjemputan?>? = null,

    @field:SerializedName("biaya_total")
    val biayaTotal: Double? = null,

    @field:SerializedName("designKustom")
    val designKustom: List<DesignKustom?>? = null,

    @field:SerializedName("id_penjahit")
    val idPenjahit: Int? = null,

    @field:SerializedName("jumlah")
    val jumlah: Int? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("detail_pesanan")
    val detailPesanan: List<DetailJahit?>? = null,

    @field:SerializedName("penawaran")
    val penawaran: Penawaran? = null,

    @field:SerializedName("id_konsumen")
    val idKonsumen: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null
) : Parcelable