package com.keerjain.crownstailor.data.entities.offer

data class OfferPrices(
    val idPesanan: Int?,
    val biayaJahit: Float?,
    val biayaMaterial: Float?,
    val biayaKirim: Float?,
    val biayaJemput: Float?,
    val hari: String,
)
