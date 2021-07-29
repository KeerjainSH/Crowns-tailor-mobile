package com.keerjain.crownstailor.data.sources.remote

import android.util.Log
import com.keerjain.crownstailor.data.entities.detail.CustomerDetail
import com.keerjain.crownstailor.data.entities.detail.OrderDetail
import com.keerjain.crownstailor.data.entities.detail.ProductDetail
import com.keerjain.crownstailor.data.entities.detail.TailorCredentials
import com.keerjain.crownstailor.data.entities.offer.Offer
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import com.keerjain.crownstailor.data.entities.offer.OfferPrices
import com.keerjain.crownstailor.data.entities.product.Product
import com.keerjain.crownstailor.data.sources.remote.posts.RegistrationData
import com.keerjain.crownstailor.data.entities.transaction.Transaction
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem
import com.keerjain.crownstailor.data.sources.remote.api.ApiService
import com.keerjain.crownstailor.data.sources.remote.posts.IdPesananPost
import com.keerjain.crownstailor.data.sources.remote.posts.LoginPost
import com.keerjain.crownstailor.data.sources.remote.responses.Data
import com.keerjain.crownstailor.data.sources.remote.responses.DataItem
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.DesignKustom
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.DetailJahit
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.Penawaran
import com.keerjain.crownstailor.utils.DataDummy
import com.keerjain.crownstailor.utils.DataMapper
import com.keerjain.crownstailor.utils.SessionManager
import com.keerjain.crownstailor.utils.SessionManager.Companion.KEY_TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource(private val api: ApiService, private val sessionManager: SessionManager) {
    fun signIn(credentials: TailorCredentials): Flow<Boolean> = flow {
        val login = if (credentials.email != null) {
            credentials.email.toString()
        } else {
            credentials.username.toString()
        }

        val data = LoginPost(
            login,
            credentials.password
        )

        val response = api.login(data)

        if (response.isSuccessful) {

            val session = DataMapper.mapCredentialsToSessionData(credentials)
            val id = response.body()?.data?.idUser

            if (id != null) {
                val token = "Bearer " + response.body()?.data?.token.toString()
                val detailResponse = api.getPenjahitDetails(token, id)
                if (detailResponse.isSuccessful) {
                    session.name = detailResponse.body()?.data?.nama.toString()
                    session.userId = response.body()?.data?.idUser
                    session.token = token
                    sessionManager.createLoginSession(session)
                    emit(true)
                } else {
                    Log.d("Detail", "Not Successful")
                    emit(false)
                }
            } else {
                Log.d("ID", "ID Null")
                emit(false)
            }
        } else {
            Log.d("Response", "Response Unsuccessful")
            emit(false)
        }
    }

    fun registerUser(registrationData: RegistrationData): Flow<Boolean> = flow {
        val response = api.register(registrationData)

        if (response.isSuccessful) {
            val session = DataMapper.mapRegistrationDataToSessionData(registrationData)
            val token = "Bearer " + response.body()?.data?.token.toString()
            session.userId = response.body()?.data?.idUser
            session.token = token
            sessionManager.createLoginSession(session)
            emit(true)
        } else {
            emit(false)
        }
    }

    fun getOfferForTailor(): Flow<List<OfferListItem>> = flow {
        val response = api.getOffers(sessionManager.getToken().toString())

        if (response.isSuccessful) {
            val listApi = response.body()?.data
            val list = ArrayList<OfferListItem>()
            if (!listApi.isNullOrEmpty()) {
                for (data in listApi) {
                    if (data != null) {
                        val userResponse = data.idKonsumen?.let { api.getCustomerDetails(it.toLong()) }
                        if (userResponse?.isSuccessful as Boolean) {
                            val offer = data.id?.let {
                                OfferListItem(
                                    offerId = it.toLong(),
                                    productName = data.baju?.nama.toString(),
                                    customerDetail = CustomerDetail(
                                        userId = data.idKonsumen.toLong(),
                                        username = userResponse.body()?.data?.username.toString(),
                                        email = userResponse.body()?.data?.email.toString()
                                    ),
                                    offerDate = data.penawaran?.createdAt.toString()
                                )
                            }

                            if (offer != null) {
                                list.add(offer)
                            }
                        } else {
                            val offer = data.id?.let {
                                OfferListItem(
                                    offerId = it.toLong(),
                                    productName = data.baju?.nama.toString(),
                                    customerDetail = CustomerDetail(
                                        userId = data.idKonsumen.toLong(),
                                        username = "null",
                                        email = "null"
                                    ),
                                    offerDate = data.penawaran?.createdAt.toString()
                                )
                            }

                            if (offer != null) {
                                list.add(offer)
                            }
                        }
                    }
                }
            }

            emit(list)
        }
    }

    fun getOrdersForTailor(): Flow<List<TransactionListItem>> = flow {
        val response = api.getOrders(sessionManager.getToken().toString())

        if (response.isSuccessful) {
            val listApi = response.body()?.data
            val list = ArrayList<TransactionListItem>()
            if (!listApi.isNullOrEmpty()) {
                for (data in listApi) {
                    if (data != null) {
                        val transaction = data.id?.let {
                            TransactionListItem(
                                trxId = it.toLong(),
                                productName = data.baju?.nama.toString(),
                                productPhoto = data.baju?.foto.toString(),
                                transactionStatus = DataMapper.mapStatusToOrderStatus(data.statusPesanan.toString().toInt())
                            )
                        }

                        if (transaction != null) {
                            list.add(transaction)
                        }
                    }
                }
            }

            emit(list)
        }
    }

    fun getOfferDetails(offerListItem: OfferListItem): Flow<Offer> = flow {
        val response = api.getOrderDetails(
            sessionManager.getToken().toString(),
            offerListItem.offerId.toInt()
        )

        if (response.isSuccessful) {
            val data = response.body()?.data

            if (data != null) {
                val userResponse = data.idKonsumen?.toLong()?.let { api.getCustomerDetails(it) }
                if (userResponse?.isSuccessful as Boolean) {
                    val offerResponse = data.penawaran
                    if (offerResponse != null) {
                        val offer = data.id?.toLong()?.let {
                            Offer(
                                offerId = it,
                                customer = CustomerDetail(
                                    userId = data.idKonsumen.toLong(),
                                    username = userResponse.body()?.data?.username.toString(),
                                    email = userResponse.body()?.data?.email.toString()
                                ),
                                productDetail = ProductDetail(
                                    productId = data.baju?.id!!.toLong(),
                                    productName = data.baju.nama.toString(),
                                    productPhoto = data.baju.foto.toString(),
                                    productDescription = data.baju.deskripsi.toString()
                                ),
                                orderDetail = DataMapper.mapDetailJahitListToOrderDetailList(data.detailPesanan as List<DetailJahit>),
                                designDetail = DataMapper.mapDesignKustomListToDesignDetail(data.designKustom as List<DesignKustom>),
                                offerDate = data.createdAt.toString(),
                                offerAmount = offerResponse.jumlahPenawaran?.toFloat(),
                                offerStatus = DataMapper.mapStatusToOfferStatus(data.penawaran.statusPenawaran?.toInt())
                            )
                        }

                        emit(offer as Offer)
                    } else {
                        val offer = data.id?.toLong()?.let {
                            Offer(
                                offerId = it,
                                customer = CustomerDetail(
                                    userId = data.idKonsumen.toLong(),
                                    username = userResponse.body()?.data?.username.toString(),
                                    email = userResponse.body()?.data?.email.toString()
                                ),
                                productDetail = ProductDetail(
                                    productId = data.baju?.id!!.toLong(),
                                    productName = data.baju.nama.toString(),
                                    productPhoto = data.baju.foto.toString(),
                                    productDescription = data.baju.deskripsi.toString()
                                ),
                                orderDetail = DataMapper.mapDetailJahitListToOrderDetailList(data.detailPesanan as List<DetailJahit>),
                                designDetail = DataMapper.mapDesignKustomListToDesignDetail(data.designKustom as List<DesignKustom>),
                                offerDate = data.createdAt.toString(),
                                offerAmount = data.biayaTotal?.toFloat(),
                                offerStatus = DataMapper.mapStatusToOfferStatus(data.penawaran?.statusPenawaran?.toInt())
                            )
                        }

                        emit(offer as Offer)
                    }
                } else {
                    val offer = data.id?.toLong()?.let {
                        Offer(
                            offerId = it,
                            customer = CustomerDetail(
                                userId = data.idKonsumen.toLong(),
                                username = "null",
                                email = "null"
                            ),
                            productDetail = ProductDetail(
                                productId = data.baju?.id!!.toLong(),
                                productName = data.baju.nama.toString(),
                                productPhoto = data.baju.foto.toString(),
                                productDescription = data.baju.deskripsi.toString()
                            ),
                            orderDetail = DataMapper.mapDetailJahitListToOrderDetailList(data.detailPesanan as List<DetailJahit>),
                            designDetail = DataMapper.mapDesignKustomListToDesignDetail(data.designKustom as List<DesignKustom>),
                            offerDate = data.createdAt.toString(),
                            offerAmount = data.biayaTotal?.toFloat(),
                            offerStatus = DataMapper.mapStatusToOfferStatus(data.penawaran?.statusPenawaran?.toInt())
                        )
                    }

                    emit(offer as Offer)
                }
            }
        }
    }

    fun getTransactionDetails(transactionListItem: TransactionListItem): Flow<Transaction> = flow {
        val response = api.getOrderDetails(
            sessionManager.getToken().toString(),
            transactionListItem.trxId.toInt()
        )

        if (response.isSuccessful) {
            val data = response.body()?.data

            if (data != null) {
                val userResponse = data.idKonsumen?.toLong()?.let { api.getCustomerDetails(it) }
                if (userResponse?.isSuccessful as Boolean) {
                    val orderList = DataMapper.mapDetailJahitListToOrderDetailList(data.detailPesanan as List<DetailJahit>)
                    val transaction = data.id?.toLong()?.let {
                        Transaction(
                            trxId = it,
                            customerDetail = CustomerDetail(
                                userId = data.idKonsumen.toLong(),
                                username = userResponse.body()?.data?.username.toString(),
                                email = userResponse.body()?.data?.email.toString()
                            ),
                            productDetail = ProductDetail(
                                productId = data.baju?.id!!.toLong(),
                                productName = data.baju.nama.toString(),
                                productPhoto = data.baju.foto.toString(),
                                productDescription = data.baju.deskripsi.toString()
                            ),
                            orderDetail = DataMapper.mapOrderDetailsListToProductListItem(
                                orderList,
                                ProductDetail(
                                    productId = data.baju.id.toLong(),
                                    productName = data.baju.nama.toString(),
                                    productPhoto = data.baju.foto.toString(),
                                    productDescription = data.baju.deskripsi.toString()
                                )
                            ),
                            designDetail = DataMapper.mapDesignKustomListToDesignDetail(data.designKustom as List<DesignKustom>),
                            totalAmount = data.biayaTotal?.toFloat(),
                            transactionStatus = DataMapper.mapStatusToOrderStatus(data.statusPesanan.toString().toInt()),
                            shipmentDetail = null
                        )
                    }

                    emit(transaction as Transaction)
                } else {
                    val orderList = DataMapper.mapDetailJahitListToOrderDetailList(data.detailPesanan as List<DetailJahit>)
                    val transaction = data.id?.toLong()?.let {
                        Transaction(
                            trxId = it,
                            customerDetail = CustomerDetail(
                                userId = data.idKonsumen.toLong(),
                                username = "null",
                                email = "null"
                            ),
                            productDetail = ProductDetail(
                                productId = data.baju?.id!!.toLong(),
                                productName = data.baju.nama.toString(),
                                productPhoto = data.baju.foto.toString(),
                                productDescription = data.baju.deskripsi.toString()
                            ),
                            orderDetail = DataMapper.mapOrderDetailsListToProductListItem(
                                orderList,
                                ProductDetail(
                                    productId = data.baju.id.toLong(),
                                    productName = data.baju.nama.toString(),
                                    productPhoto = data.baju.foto.toString(),
                                    productDescription = data.baju.deskripsi.toString()
                                )
                            ),
                            designDetail = DataMapper.mapDesignKustomListToDesignDetail(data.designKustom as List<DesignKustom>),
                            totalAmount = data.biayaTotal?.toFloat(),
                            transactionStatus = DataMapper.mapStatusToOrderStatus(data.statusPesanan.toString().toInt()),
                            shipmentDetail = null
                        )
                    }

                    emit(transaction as Transaction)
                }
            }
        }
    }

    fun acceptOffer(idPesanan: Int): Flow<Boolean> = flow {
        val token = sessionManager.getToken()
        val data = IdPesananPost(
            idPesanan
        )

        if (token != null && token != "null") {
            val response = api.acceptOffer(token, data)
            if (response.isSuccessful) {
                emit(true)
            } else {
                emit(false)
            }
        } else {
            emit(false)
            Log.d("Error", "Gagal Mendapatkan Token: Token not found on prefs")
        }
    }

    fun declineOffer(idPesanan: Int): Flow<Boolean> = flow {
        val token = sessionManager.getToken()
        val data = IdPesananPost(
            idPesanan
        )

        if (token != null && token != "null") {
            val response = api.declineOffer(token, data)

            if (response.isSuccessful) {
                emit(true)
            } else {
                emit(false)
            }
        } else {
            emit(false)
            Log.d("Error", "Gagal Mendapatkan Token: Token not found on prefs")
        }
    }

    fun setPrice(prices: OfferPrices): Flow<Penawaran> = flow {
        val token = sessionManager.getToken()
        val data = DataMapper.mapOfferPriceToPostHarga(prices)

        if (token != null && token != "null") {
            val response = api.fillPrice(token, data)

            if (response.isSuccessful) {
                val penawaran = response.body()?.data?.penawaran
                emit(penawaran as Penawaran)
            } else {
                emit(Penawaran())
            }
        } else {
            emit(Penawaran())
        }
    }

    fun getCatalog(): Flow<List<Product>> = flow {
        val response = api.getCatalog()

        if (response.isSuccessful) {
            val catalogResponse = response.body()
            if (catalogResponse != null) {
                val list = DataMapper.mapCatalogResponseListToProductList(catalogResponse.data as List<DataItem>)
                emit(list as List<Product>)
            } else {
                emit(ArrayList<Product>())
            }
        } else {
            emit(ArrayList<Product>())
        }
    }
}