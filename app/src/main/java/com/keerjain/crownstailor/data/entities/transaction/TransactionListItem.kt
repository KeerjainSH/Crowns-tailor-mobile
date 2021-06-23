package com.keerjain.crownstailor.data.entities.transaction

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionListItem(
    val trxId: Long,
    val productName: String,
    val productSubtitle: String,
    val transactionStatus: String,
) : Parcelable
