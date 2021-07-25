package com.keerjain.crownstailor.data.sources.remote

import android.util.Log
import com.keerjain.crownstailor.data.entities.detail.TailorCredentials
import com.keerjain.crownstailor.data.entities.offer.Offer
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import com.keerjain.crownstailor.data.sources.remote.posts.RegistrationData
import com.keerjain.crownstailor.data.entities.transaction.Transaction
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem
import com.keerjain.crownstailor.data.sources.remote.api.ApiService
import com.keerjain.crownstailor.data.sources.remote.posts.LoginPost
import com.keerjain.crownstailor.utils.DataDummy
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

        val response = api.login(data)

        if (response.isSuccessful) {

            val session = DataMapper.mapCredentialsToSessionData(credentials)
            val id = response.body()?.data?.idUser

            if (id != null) {
                val detailResponse = api.getPenjahitDetails(id)
                if (detailResponse.isSuccessful) {
                    session.name = detailResponse.body()?.data?.nama.toString()
                    session.userId = response.body()?.data?.idUser
                    session.token = response.body()?.data?.token
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

    fun getOfferForTailor(tailorId: Long): Flow<List<OfferListItem>> = flow {
        val list = DataDummy.generateDummyOffer(tailorId)

        emit(list)
    }

    fun getOrdersForTailor(tailorId: Long): Flow<List<TransactionListItem>> = flow {
        val list = DataDummy.generateDummyOrder(tailorId)

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
}