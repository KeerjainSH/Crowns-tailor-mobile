package com.keerjain.crownstailor.data.entities.offer

import android.os.Parcelable
import com.keerjain.crownstailor.data.entities.detail.CustomerDetail
import com.keerjain.crownstailor.data.entities.detail.DesignDetail
import com.keerjain.crownstailor.data.entities.detail.OrderDetail
import com.keerjain.crownstailor.data.entities.detail.ProductDetail
import com.keerjain.crownstailor.utils.enums.OfferStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class Offer(
    val offerId: Long,
    val customer: CustomerDetail,
    val productDetail: ProductDetail,
    val orderDetail: List<OrderDetail>,
    val designDetail: List<DesignDetail>,
    val offerDate: String,
    val offerAmount: Float?,
    val offerStatus: OfferStatus,
) : Parcelable