package com.keerjain.crownstailor.data.entities.transaction

import com.keerjain.crownstailor.data.entities.detail.CustomerDetail
import com.keerjain.crownstailor.data.entities.detail.ProductDetail

data class Transaction(
    val trxId: Long,
    val productDetail: ProductDetail,
    val customerDetail: CustomerDetail,
    val amount: Int,
    val totalPrice: Float,
    val transactionStatus: String,
)
