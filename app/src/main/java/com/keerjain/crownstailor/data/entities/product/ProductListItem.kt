package com.keerjain.crownstailor.data.entities.product

import android.os.Parcelable
import com.keerjain.crownstailor.data.entities.detail.OrderDetail
import com.keerjain.crownstailor.data.entities.detail.ProductDetail
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductListItem(
    val productDetail: ProductDetail,
    val orderDetail: OrderDetail
) : Parcelable
