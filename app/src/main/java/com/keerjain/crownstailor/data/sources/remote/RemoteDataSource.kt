package com.keerjain.crownstailor.data.sources.remote

import android.util.Log
import com.keerjain.crownstailor.data.entities.detail.CustomerDetail
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
import com.keerjain.crownstailor.data.sources.remote.responses.DataItem
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
            emit(true)
            val session = DataMapper.mapRegistrationDataToSessionData(registrationData)
            session.userId = response.body()?.data?.idUser
            session.token = response.body()?.data?.token
            sessionManager.createLoginSession(session)
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
                        }
                    }
                }
            }

            emit(list)
        }
    }

    fun getOrdersForTailor(): Flow<List<TransactionListItem>> = flow {
        val list = DataDummy.generateDummyOrder()

        emit(list)
    }

    fun getOfferDetails(offerListItem: OfferListItem): Flow<Offer> = flow {
        val offer = DataDummy.generateOfferDetails(offerListItem)

        emit(offer)
    }

    fun getTransactionDetails(transactionListItem: TransactionListItem): Flow<Transaction> = flow {
        val transaction = DataDummy.generateTransactionDetails(transactionListItem)

        emit(transaction)
    }

    fun acceptOffer(idPesanan: Int): Flow<Boolean> = flow {
        val token = sessionManager.getToken()
        val data = IdPesananPost(
            idPesanan
        )

        if (token != null && token != "null") {
            api.acceptOffer(token, data)
            emit(true)
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
            api.declineOffer(token, data)
            emit(true)
        } else {
            emit(false)
            Log.d("Error", "Gagal Mendapatkan Token: Token not found on prefs")
        }
    }

    fun setPrice(prices: OfferPrices): Flow<Boolean> = flow {
        val token = sessionManager.getToken()
        val data = DataMapper.mapOfferPriceToPostHarga(prices)

        if (token != null && token != "null") {
            api.fillPrice(token, data)
            emit(true)
        } else {
            emit(false)
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