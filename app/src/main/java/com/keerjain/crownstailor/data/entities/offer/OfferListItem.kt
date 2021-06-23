package com.keerjain.crownstailor.data.entities.offer

import com.keerjain.crownstailor.data.entities.detail.CustomerDetail

data class OfferListItem(
    val offerId: Long,
    val productName: String,
    val customerDetail: CustomerDetail,
    val offerDate: String,
)
