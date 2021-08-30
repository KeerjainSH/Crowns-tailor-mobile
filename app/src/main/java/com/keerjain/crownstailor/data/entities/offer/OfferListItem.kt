package com.keerjain.crownstailor.data.entities.offer

import android.os.Parcelable
import com.keerjain.crownstailor.data.entities.detail.CustomerDetail
import com.keerjain.crownstailor.utils.enums.OfferStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class OfferListItem(
    val offerId: Long,
    val productName: String,
    val customerDetail: CustomerDetail,
    val offerDate: String,
    val offerStatus: OfferStatus
) : Parcelable
