package com.keerjain.crownstailor.data.entities.transaction

import android.os.Parcelable
import com.keerjain.crownstailor.data.entities.detail.CustomerDetail
import com.keerjain.crownstailor.data.entities.detail.DesignDetail
import com.keerjain.crownstailor.data.entities.detail.ProductDetail
import com.keerjain.crownstailor.data.entities.detail.ShipmentDetail
import com.keerjain.crownstailor.data.entities.product.ProductListItem
import com.keerjain.crownstailor.utils.enums.Status
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val trxId: Long,
    val productDetail: ProductDetail,
    val orderDetail: List<ProductListItem>,
    val designDetail: List<DesignDetail>,
    val customerDetail: CustomerDetail,
    val shipmentDetail: List<ShipmentDetail>?,
    val totalAmount: Float?,
    val transactionStatus: Status,
) : Parcelable
