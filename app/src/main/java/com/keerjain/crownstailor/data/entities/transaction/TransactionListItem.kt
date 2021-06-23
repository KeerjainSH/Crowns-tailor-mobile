package com.keerjain.crownstailor.data.entities.transaction

data class TransactionListItem(
    val trxId: Long,
    val productName: String,
    val productSubtitle: String,
    val transactionStatus: String,
)
