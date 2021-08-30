package com.keerjain.crownstailor.data.sources.remote

import android.util.Log
import com.keerjain.crownstailor.data.entities.detail.CustomerDetail
import com.keerjain.crownstailor.data.entities.detail.ProductDetail
import com.keerjain.crownstailor.data.entities.detail.TailorCredentials
import com.keerjain.crownstailor.data.entities.offer.Offer
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import com.keerjain.crownstailor.data.entities.offer.OfferPrices
import com.keerjain.crownstailor.data.entities.product.Product
import com.keerjain.crownstailor.data.entities.transaction.Transaction
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem
import com.keerjain.crownstailor.data.sources.remote.api.ApiService
import com.keerjain.crownstailor.data.sources.remote.posts.IdPesananPost
import com.keerjain.crownstailor.data.sources.remote.posts.LoginPost
import com.keerjain.crownstailor.data.sources.remote.posts.ProfileUpdatePost
import com.keerjain.crownstailor.data.sources.remote.posts.RegistrationData
import com.keerjain.crownstailor.data.sources.remote.responses.DataItem
import com.keerjain.crownstailor.data.sources.remote.utils.entities.penjahit.DetailPenjahit
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.DesignKustom
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.DetailJahit
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.LokasiPenjemputan
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.Penawaran
import com.keerjain.crownstailor.utils.DataMapper
import com.keerjain.crownstailor.utils.SessionManager
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

        try {
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
        } catch (e: Exception) {
            emit(false)
        }
    }

    fun registerUser(registrationData: RegistrationData): Flow<Boolean> = flow {

        try {
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
        } catch (e: Exception) {
            emit(false)
        }
    }

    fun getOfferForTailor(): Flow<List<OfferListItem>> = flow {

        try {
            val response = api.getOffers(sessionManager.getToken().toString())

            if (response.isSuccessful) {
                val listApi = response.body()?.data
                val list = ArrayList<OfferListItem>()
                if (!listApi.isNullOrEmpty()) {
                    for (data in listApi) {
                        if (data != null) {
                            val userResponse =
                                data.idKonsumen?.let { api.getCustomerDetails(it.toLong()) }
                            if (userResponse?.isSuccessful as Boolean) {
                                val offer = data.id?.let {
                                    OfferListItem(
                                        offerId = it.toLong(),
                                        productName = data.baju?.nama.toString(),
                                        customerDetail = CustomerDetail(
                                            userId = data.idKonsumen.toLong(),
                                            username = userResponse.body()?.data?.username.toString(),
                                            email = userResponse.body()?.data?.email.toString(),
                                            name = userResponse.body()?.data?.name.toString()
                                        ),
                                        offerDate = data.penawaran?.createdAt.toString(),
                                        offerStatus = DataMapper.mapStatusToOfferStatus(data.penawaran?.statusPenawaran?.toInt())
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
                                            email = "null",
                                            name = "null"
                                        ),
                                        offerDate = data.penawaran?.createdAt.toString(),
                                        offerStatus = DataMapper.mapStatusToOfferStatus(data.penawaran?.statusPenawaran?.toInt())
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
        } catch (e: Exception) {
            emit(ArrayList<OfferListItem>())
        }
    }

    fun getOrdersForTailor(): Flow<List<TransactionListItem>> = flow {
        try {
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
                                    transactionStatus = DataMapper.mapStatusToOrderStatus(
                                        data.statusPesanan.toString().toInt()
                                    )
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
        } catch (e: Exception) {
            emit(ArrayList<TransactionListItem>())
        }
    }

    fun getOfferDetails(offerListItem: OfferListItem): Flow<Offer> = flow {
        try {
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
                                        email = userResponse.body()?.data?.email.toString(),
                                        name = userResponse.body()?.data?.name.toString()
                                    ),
                                    productDetail = ProductDetail(
                                        productId = data.baju?.id!!.toLong(),
                                        productName = data.baju.nama.toString(),
                                        productPhoto = data.baju.foto.toString(),
                                        productDescription = data.baju.deskripsi.toString()
                                    ),
                                    orderDetail = DataMapper.mapDetailJahitListToOrderDetailList(
                                        data.detailPesanan as List<DetailJahit>
                                    ),
                                    designDetail = DataMapper.mapDesignKustomListToDesignDetail(data.designKustom as List<DesignKustom>),
                                    offerDate = data.createdAt.toString(),
                                    offerAmount = offerResponse.jumlahPenawaran?.toFloat(),
                                    offerEstimation = offerResponse.hariTawar,
                                    offerStatus = DataMapper.mapStatusToOfferStatus(data.penawaran.statusPenawaran?.toInt()),
                                    lokasiPenjemputan = DataMapper.mapLokasiPenjemputanListToShipmentDetailList(
                                        data.lokasiPenjemputan as List<LokasiPenjemputan>,
                                        userResponse.body()?.data?.name.toString()
                                    )
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
                                        email = userResponse.body()?.data?.email.toString(),
                                        name = userResponse.body()?.data?.name.toString()
                                    ),
                                    productDetail = ProductDetail(
                                        productId = data.baju?.id!!.toLong(),
                                        productName = data.baju.nama.toString(),
                                        productPhoto = data.baju.foto.toString(),
                                        productDescription = data.baju.deskripsi.toString()
                                    ),
                                    orderDetail = DataMapper.mapDetailJahitListToOrderDetailList(
                                        data.detailPesanan as List<DetailJahit>
                                    ),
                                    designDetail = DataMapper.mapDesignKustomListToDesignDetail(data.designKustom as List<DesignKustom>),
                                    offerDate = data.createdAt.toString(),
                                    offerAmount = data.biayaTotal?.toFloat(),
                                    offerEstimation = null,
                                    offerStatus = DataMapper.mapStatusToOfferStatus(data.penawaran?.statusPenawaran?.toInt()),
                                    lokasiPenjemputan = DataMapper.mapLokasiPenjemputanListToShipmentDetailList(
                                        data.lokasiPenjemputan as List<LokasiPenjemputan>,
                                        userResponse.body()?.data?.name.toString()
                                    )
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
                                    email = "null",
                                    name = "null"
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
                                offerEstimation = null,
                                offerStatus = DataMapper.mapStatusToOfferStatus(data.penawaran?.statusPenawaran?.toInt()),
                                lokasiPenjemputan = DataMapper.mapLokasiPenjemputanListToShipmentDetailList(
                                    data.lokasiPenjemputan as List<LokasiPenjemputan>,
                                    "null"
                                )
                            )
                        }

                        emit(offer as Offer)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("OfferDetailError", "Error: $e")
        }
    }

    fun getTransactionDetails(transactionListItem: TransactionListItem): Flow<Transaction> = flow {
        try {
            val response = api.getOrderDetails(
                sessionManager.getToken().toString(),
                transactionListItem.trxId.toInt()
            )

            if (response.isSuccessful) {
                val data = response.body()?.data

                if (data != null) {
                    val userResponse = data.idKonsumen?.toLong()?.let { api.getCustomerDetails(it) }
                    if (userResponse?.isSuccessful as Boolean) {
                        val orderList =
                            DataMapper.mapDetailJahitListToOrderDetailList(data.detailPesanan as List<DetailJahit>)
                        val transaction = data.id?.toLong()?.let {
                            Transaction(
                                trxId = it,
                                customerDetail = CustomerDetail(
                                    userId = data.idKonsumen.toLong(),
                                    username = userResponse.body()?.data?.username.toString(),
                                    email = userResponse.body()?.data?.email.toString(),
                                    name = userResponse.body()?.data?.name.toString()
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
                                transactionStatus = DataMapper.mapStatusToOrderStatus(
                                    data.statusPesanan.toString().toInt()
                                ),
                                shipmentDetail = DataMapper.mapLokasiPenjemputanListToShipmentDetailList(
                                    data.lokasiPenjemputan as List<LokasiPenjemputan>,
                                    userResponse.body()?.data?.name.toString(),
                                )
                            )
                        }

                        emit(transaction as Transaction)
                    } else {
                        val orderList =
                            DataMapper.mapDetailJahitListToOrderDetailList(data.detailPesanan as List<DetailJahit>)
                        val transaction = data.id?.toLong()?.let {
                            Transaction(
                                trxId = it,
                                customerDetail = CustomerDetail(
                                    userId = data.idKonsumen.toLong(),
                                    username = "null",
                                    email = "null",
                                    name = "null",
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
                                transactionStatus = DataMapper.mapStatusToOrderStatus(
                                    data.statusPesanan.toString().toInt()
                                ),
                                shipmentDetail = null
                            )
                        }

                        emit(transaction as Transaction)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("OrderDetailError", "Error: $e")
        }
    }

    fun acceptOffer(idPesanan: Int): Flow<Boolean> = flow {
        try {
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
        } catch (e: Exception) {
            Log.d("AcceptOfferError", "Error: $e")
            emit(false)
        }
    }

    fun declineOffer(idPesanan: Int): Flow<Boolean> = flow {
        try {
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
        } catch (e: Exception) {
            Log.d("DeclineOfferError", "Error: $e")
            emit(false)
        }
    }

    fun setPrice(prices: OfferPrices): Flow<Penawaran> = flow {
        try {
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
        } catch (e: Exception) {
            Log.d("SetPriceError", "Error: $e")
            emit(Penawaran())
        }
    }

    fun getCatalog(): Flow<List<Product>> = flow {
        try {
            val response = api.getCatalog()

            if (response.isSuccessful) {
                val catalogResponse = response.body()
                if (catalogResponse != null) {
                    val list =
                        DataMapper.mapCatalogResponseListToProductList(catalogResponse.data as List<DataItem>)
                    emit(list as List<Product>)
                } else {
                    emit(ArrayList<Product>())
                }
            } else {
                emit(ArrayList<Product>())
            }
        } catch (e: Exception) {
            Log.d("GetCatalogError", "Error: $e")
            emit(ArrayList<Product>())
        }
    }

    fun getTailorDetails(): Flow<ProfileUpdatePost> = flow {
        try {
            val response = api.getPenjahitDetails(
                sessionManager.getToken().toString(),
                sessionManager.getUserId()
            )

            if (response.isSuccessful) {
                Log.d("TailorDetails", response.isSuccessful.toString())
                val profile =
                    DataMapper.mapDetailPenjahitToProfileUpdatePost(response.body()?.data as DetailPenjahit)
                emit(profile)
            } else {
                Log.d("TailorDetailsError", "Error: Response Unsucessful")
                emit(ProfileUpdatePost())
            }
        } catch (e: Exception) {
            Log.d("TailorDetailsError", "Error: $e")
            emit(ProfileUpdatePost())
        }
    }

    fun updateTailorProfile(profile: ProfileUpdatePost): Flow<Boolean> = flow {
        try {
            val response = api.updatePenjahitDetails(sessionManager.getToken().toString(), profile)

            if (response.isSuccessful) {
                val fullName = response.body()?.data?.nama.toString()
                sessionManager.changeLoginFullName(fullName)
            }

            emit(response.isSuccessful)
        } catch (e: Exception) {
            Log.d("UpdateTailorError", "Error: $e")
            emit(false)
        }
    }

    fun getRating(): Flow<Float?> = flow {
        try {
            val response = api.getPenjahitDetails(
                sessionManager.getToken().toString(),
                sessionManager.getUserId()
            )

            if (response.isSuccessful) {
                val rating = response.body()?.data?.rating

                if (rating != null) {
                    emit(rating)
                } else {
                    emit(null)
                }
            } else {
                emit(null)
            }
        } catch (e: Exception) {
            Log.d("GetRatingError", "Error: $e")
            emit(null)
        }
    }
}