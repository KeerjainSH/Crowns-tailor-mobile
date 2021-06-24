package com.keerjain.crownstailor.data.entities.transaction

import android.os.Parcelable
import com.keerjain.crownstailor.utils.enums.Status
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionListItem(
    val trxId: Long,
    val productName: String,
    val productPhoto: String,
    val transactionStatus: Status,
) : Parcelable
