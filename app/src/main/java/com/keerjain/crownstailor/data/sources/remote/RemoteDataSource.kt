package com.keerjain.crownstailor.data.sources.remote

import com.keerjain.crownstailor.data.entities.detail.TailorCredentials
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem
import com.keerjain.crownstailor.data.sources.remote.api.ApiService
import com.keerjain.crownstailor.utils.DataDummy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource(private val api: ApiService) {
    fun registerUser(tailor: TailorCredentials): Boolean {
        return true
    }

    fun getOfferForTailor(tailorId: Long): Flow<List<OfferListItem>> = flow {
        val list = DataDummy.generateDummyOffer(tailorId)

        emit(list)
    }

    fun getOrdersForTailor(tailorId: Long): Flow<List<TransactionListItem>> = flow {
        val list = DataDummy.generateDummyOrder(tailorId)

        emit(list)
    }
}