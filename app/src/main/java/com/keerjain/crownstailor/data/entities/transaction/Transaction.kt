package com.keerjain.crownstailor.data.entities.transaction

import android.os.Parcelable
import com.keerjain.crownstailor.data.entities.detail.CustomerDetail
import com.keerjain.crownstailor.data.entities.detail.ProductDetail
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val trxId: Long,
    val productDetail: ProductDetail,
    val customerDetail: CustomerDetail,
    val amount: Int,
    val totalPrice: Float,
    val transactionStatus: String,
) : Parcelable
