package com.keerjain.crownstailor.utils

import com.keerjain.crownstailor.data.entities.detail.CustomerDetail
import com.keerjain.crownstailor.data.entities.detail.OrderDetail
import com.keerjain.crownstailor.data.entities.detail.ProductDetail
import com.keerjain.crownstailor.data.entities.offer.Offer
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import com.keerjain.crownstailor.data.entities.product.Product
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem
import com.keerjain.crownstailor.utils.enums.OfferStatus
import com.keerjain.crownstailor.utils.enums.Status

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

        list.add(
            OfferListItem(
                offerId = 2L,
                productName = "Seragam SMA",
                offerDate = "5 Juni 2021",
                customerDetail = CustomerDetail(
                    userId = 1L,
                    userFullName = "Rio Darwin",
                    username = "riodarwin",
                    email = "riodarwin@gmail.com",
                    photoProfile = "https://picsum.photos/400"
                )
            )
        )

        list.add(
            OfferListItem(
                offerId = 3L,
                productName = "Seragam SMA",
                offerDate = "6 Juni 2021",
                customerDetail = CustomerDetail(
                    userId = 1L,
                    userFullName = "Abdi Cipta",
                    username = "abdicipta11",
                    email = "abdicipta11@gmail.com",
                    photoProfile = "https://picsum.photos/400"
                )
            )
        )

        return list
    }

    fun generateDummyOrder(tailorId: Long): List<TransactionListItem> {
        val list = ArrayList<TransactionListItem>()

        list.add(
            TransactionListItem(
                1L,
                "Seragam SMP",
                "https://picsum.photos/400",
                Status.FINISHED
            )
        )

        list.add(
            TransactionListItem(
                2L,
                "Seragam SMP",
                "https://picsum.photos/400",
                Status.ON_DELIVERY
            )
        )

        list.add(
            TransactionListItem(
                3L,
                "Seragam SMA",
                "https://picsum.photos/400",
                Status.ON_PROGRESS
            )
        )

        list.add(
            TransactionListItem(
                4L,
                "Seragam SMA",
                "https://picsum.photos/400",
                Status.NEW_ORDER
            )
        )

        return list
    }

    fun generateProducts(): List<Product> {
        val list = ArrayList<Product>()

        list.add(
            Product(
                1,
                "Seragam SD"
            )
        )

        list.add(
            Product(
                2,
                "Seragam SMP"
            )
        )

        list.add(
            Product(
                3,
                "Seragam SMA"
            )
        )

        list.add(
            Product(
                4,
                "Kemeja"
            )
        )

        list.add(
            Product(
                5,
                "Jas"
            )
        )

        return list
    }

    fun generateOfferDetails(offerListItem: OfferListItem) = Offer(
        offerId = offerListItem.offerId,
        customer = offerListItem.customerDetail,
        productDetail = ProductDetail(
            2, offerListItem.productName, "https://picsum.photos/400"
        ),
        orderDetail = OrderDetail(
            armSize = 40f,
            waistSize = 105f,
            chestSize = 68f,
            neckSize = 32f,
            bodyHeight = 175f,
            bodyWeight = 82f,
            instructions = "-"
        ),
        design = "https://linkedin.com/in/michael-ricky",
        offerDate = offerListItem.offerDate,
        offerAmount = null,
        offerEstimation = null,
        offerStatus = OfferStatus.NEW_OFFER,
    )
}