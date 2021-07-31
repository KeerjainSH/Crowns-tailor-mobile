package com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailPesanan(
    @SerializedName("pembayaran")
    val pembayaran: Pembayaran? = null,

    @SerializedName("rating")
    val rating: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("status_pesanan")
    val statusPesanan: String? = null,

    @SerializedName("lokasi_penjemputan")
    val lokasiPenjemputan: List<LokasiPenjemputan?>? = null,

    @SerializedName("biaya_total")
    val biayaTotal: Double? = null,

    @SerializedName("design_kustom")
    val designKustom: List<DesignKustom?>? = null,

    @SerializedName("id_penjahit")
    val idPenjahit: Int? = null,

    @SerializedName("jumlah")
    val jumlah: Int? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("detail_pesanan")
    val detailPesanan: List<DetailJahit?>? = null,

    @SerializedName("id_baju")
    val idBaju: Int? = null,

    @SerializedName("penawaran")
    val penawaran: Penawaran? = null,

    @SerializedName("id_konsumen")
    val idKonsumen: Int? = null,

    @SerializedName("id")
    val id: Int? = null
) : Parcelable
