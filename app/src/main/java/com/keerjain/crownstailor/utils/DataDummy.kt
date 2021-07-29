package com.keerjain.crownstailor.utils

import com.keerjain.crownstailor.data.entities.detail.CustomerDetail
import com.keerjain.crownstailor.data.entities.detail.OrderDetail
import com.keerjain.crownstailor.data.entities.detail.ProductDetail
import com.keerjain.crownstailor.data.entities.detail.ShipmentDetail
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import com.keerjain.crownstailor.data.entities.product.Product
import com.keerjain.crownstailor.data.entities.transaction.Transaction
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem
import com.keerjain.crownstailor.utils.enums.Status

object DataDummy {
    fun generateDummyOffer(): List<OfferListItem> {
        val list = ArrayList<OfferListItem>()

        list.add(
            OfferListItem(
                offerId = 1L,
                productName = "Seragam SMP",
                offerDate = "5 Juni 2021",
                customerDetail = CustomerDetail(
                    userId = 1L,
                    username = "ritakusuma23",
                    email = "ritakusuma23@gmail.com",
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
                    username = "riodarwin",
                    email = "riodarwin@gmail.com",
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
                    username = "abdicipta11",
                    email = "abdicipta11@gmail.com",
                )
            )
        )

        return list
    }

    fun generateDummyOrder(): List<TransactionListItem> {
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
                Status.PAID_ORDER
            )
        )

        list.add(
            TransactionListItem(
                3L,
                "Seragam SMA",
                "https://picsum.photos/400",
                Status.PAID_ORDER
            )
        )

        list.add(
            TransactionListItem(
                4L,
                "Seragam SMA",
                "https://picsum.photos/400",
                Status.FINISHED
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

//    fun generateOfferDetails(offerListItem: OfferListItem) = Offer(
//        offerId = offerListItem.offerId,
//        customer = offerListItem.customerDetail,
//        productDetail = ProductDetail(
//            2, offerListItem.productName, "https://picsum.photos/400"
//        ),
//        orderDetail = OrderDetail(
//            armSize = 40f,
//            waistSize = 105f,
//            chestSize = 68f,
//            neckSize = 32f,
//            bodyHeight = 175f,
//            bodyWeight = 82f,
//            instructions = "-",
//        ),
//        offerDate = offerListItem.offerDate,
//        offerAmount = null,
//        offerEstimation = null,
//        offerStatus = OfferStatus.NEW_OFFER,
//    )

//    fun generateTransactionDetails(transactionListItem: TransactionListItem) = Transaction(
//        trxId = transactionListItem.trxId,
//        productList = arrayListOf(
//            ProductListItem(
//                productDetail = ProductDetail(
//                    productId = 1L,
//                    productName = transactionListItem.productName,
//                    productPhoto = transactionListItem.productPhoto,
//                    productDescription = "Test"
//                ),
//                orderDetail = OrderDetail(
//                    armSize = 36f,
//                    waistSize = 102f,
//                    chestSize = 78f,
//                    neckSize = 27f,
//                    bodyHeight = 180f,
//                    bodyWeight = 85f,
//                    instructions = "-",
//                )
//            ),
//            ProductListItem(
//                productDetail = ProductDetail(
//                    productId = 1L,
//                    productName = transactionListItem.productName,
//                    productPhoto = transactionListItem.productPhoto,
//                    productDescription = "Test"
//                ),
//                orderDetail = OrderDetail(
//                    armSize = 34f,
//                    waistSize = 98f,
//                    chestSize = 74f,
//                    neckSize = 24f,
//                    bodyHeight = 182f,
//                    bodyWeight = 76f,
//                    instructions = "-",
//                )
//            ),
//        ),
//        customerDetail = CustomerDetail(
//            userId = 1L,
//            username = "djtyranix",
//            email = "djtyranix@gmail.com",
//        ),
//        shipmentDetail = ShipmentDetail(
//            receiverName = "Toni",
//            receiverPhoneNumber = "081822394023",
//            receiverAddress = "Jalan Guntur Raya No. 46, Setiabudi, RT.8/RW.2, Guntur, Setiabudi, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta, 12960",
//            shipmentFee = 9000f
//        ),
//        totalAmount = 120000f,
//        transactionStatus = transactionListItem.transactionStatus
//    )
}