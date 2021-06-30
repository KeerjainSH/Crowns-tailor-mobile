package com.keerjain.crownstailor.data.entities.offer

import android.os.Parcelable
import com.keerjain.crownstailor.data.entities.detail.CustomerDetail
import com.keerjain.crownstailor.data.entities.detail.OrderDetail
import com.keerjain.crownstailor.data.entities.detail.ProductDetail
import com.keerjain.crownstailor.utils.enums.OfferStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class Offer(
    val offerId: Long,
    val customer: CustomerDetail,
    val productDetail: ProductDetail,
    val orderDetail: OrderDetail,
    val offerDate: String,
    val offerAmount: Float?,
    val offerEstimation: String?,
    val offerStatus: OfferStatus,
) : Parcelable