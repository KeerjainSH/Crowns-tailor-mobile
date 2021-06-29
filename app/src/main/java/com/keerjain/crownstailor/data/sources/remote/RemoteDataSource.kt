package com.keerjain.crownstailor.data.sources.remote

import com.keerjain.crownstailor.data.entities.detail.TailorCredentials
import com.keerjain.crownstailor.data.entities.offer.Offer
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import com.keerjain.crownstailor.data.entities.post.RegistrationData
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem
import com.keerjain.crownstailor.data.sources.remote.api.ApiService
import com.keerjain.crownstailor.utils.DataDummy
import com.keerjain.crownstailor.utils.DataMapper
import com.keerjain.crownstailor.utils.SessionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource(private val api: ApiService, private val sessionManager: SessionManager) {
    fun signIn(credentials: TailorCredentials): Flow<Boolean> = flow {
        emit(true)
        sessionManager.createLoginSession(DataMapper.mapCredentialsToSessionData(credentials))
    }

    fun registerUser(registrationData: RegistrationData): Flow<Boolean> = flow {
        emit(true)
        sessionManager.createLoginSession(DataMapper.mapRegistrationDataToSessionData(registrationData))
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
}