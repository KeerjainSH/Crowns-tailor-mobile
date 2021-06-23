package com.keerjain.crownstailor.utils

import com.keerjain.crownstailor.data.entities.detail.CustomerDetail
import com.keerjain.crownstailor.data.entities.offer.OfferListItem

object DataDummy {
    fun generateDummyOffer(tailorId: Long): List<OfferListItem> {
        val list = ArrayList<OfferListItem>()

        list.add(
            OfferListItem(
                offerId = 1L,
                productName = "Seragam SMP",
                offerDate = "5 Juni 2021",
                customerDetail = CustomerDetail(
                    userId = 1L,
                    userFullName = "Rita Kusuma",
                    username = "ritakusuma23",
                    email = "ritakusuma23@gmail.com",
                    photoProfile = "https://picsum.photos/400"
                )
            )
        )

        return list
    }
}