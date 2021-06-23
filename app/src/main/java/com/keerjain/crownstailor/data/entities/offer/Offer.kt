package com.keerjain.crownstailor.data.entities.offer

import android.os.Parcelable
import com.keerjain.crownstailor.data.entities.detail.CustomerDetail
import com.keerjain.crownstailor.data.entities.detail.OrderDetail
import com.keerjain.crownstailor.data.entities.detail.ProductDetail
import kotlinx.parcelize.Parcelize

@Parcelize
data class Offer(
    val offerId: Long,
    val customer: CustomerDetail,
    val productDetail: ProductDetail,
    val orderDetail: OrderDetail,
    val design: String?,
    val offerDate: String,
    val offerAmount: Float,
    val offerStatus: String,
) : Parcelable