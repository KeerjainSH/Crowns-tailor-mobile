package com.keerjain.crownstailor.data

import com.keerjain.crownstailor.data.entities.detail.TailorCredentials
import com.keerjain.crownstailor.data.entities.offer.Offer
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import com.keerjain.crownstailor.data.entities.post.RegistrationData
import com.keerjain.crownstailor.data.entities.transaction.Transaction
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun signIn(credentials: TailorCredentials): Flow<Boolean>
    fun register(registrationData: RegistrationData): Flow<Boolean>
    fun getOfferForTailor(tailorId: Long): Flow<List<OfferListItem>>
    fun getOrdersForTailor(tailorId: Long): Flow<List<TransactionListItem>>
    fun getOfferDetails(offerListItem: OfferListItem): Flow<Offer>
    fun getTransactionDetails(transactionListItem: TransactionListItem): Flow<Transaction>
}