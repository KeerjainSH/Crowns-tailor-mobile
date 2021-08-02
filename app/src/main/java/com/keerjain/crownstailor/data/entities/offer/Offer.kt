package com.keerjain.crownstailor.data.entities.offer

import android.os.Parcelable
import com.keerjain.crownstailor.data.entities.detail.*
import com.keerjain.crownstailor.utils.enums.OfferStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class Offer(
    val offerId: Long,
    val customer: CustomerDetail,
    val productDetail: ProductDetail,
    val orderDetail: List<OrderDetail>,
    val designDetail: List<DesignDetail>,
    val lokasiPenjemputan: List<ShipmentDetail>?,
    val offerDate: String,
    val offerAmount: Float?,
    val offerEstimation: String?,
    val offerStatus: OfferStatus,
) : Parcelable