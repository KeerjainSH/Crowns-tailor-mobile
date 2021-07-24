package com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailPesanan(
    val pembayaran: Pembayaran? = null,
    val rating: String? = null,
    val createdAt: String? = null,
    val statusPesanan: String? = null,
    val lokasiPenjemputan: List<LokasiPenjemputan?>? = null,
    val biayaTotal: Double? = null,
    val designKustom: List<DesignKustom?>? = null,
    val idPenjahit: Int? = null,
    val jumlah: Int? = null,
    val updatedAt: String? = null,
    val detailPesanan: List<DetailJahit?>? = null,
    val idBaju: Int? = null,
    val penawaran: Penawaran? = null,
    val idKonsumen: Int? = null,
    val id: Int? = null
) : Parcelable
