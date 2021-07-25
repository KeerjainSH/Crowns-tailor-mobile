package com.keerjain.crownstailor.data.sources

import com.keerjain.crownstailor.data.AppRepository
import com.keerjain.crownstailor.data.entities.detail.TailorCredentials
import com.keerjain.crownstailor.data.entities.offer.Offer
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import com.keerjain.crownstailor.data.sources.remote.posts.RegistrationData
import com.keerjain.crownstailor.data.entities.transaction.Transaction
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem
import com.keerjain.crownstailor.data.sources.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class AppRepositoryImpl(
    private val remote: RemoteDataSource
) : AppRepository {
    override fun signIn(credentials: TailorCredentials): Flow<Boolean> {
        return remote.signIn(credentials)
    }

    override fun register(registrationData: RegistrationData): Flow<Boolean> {
        return remote.registerUser(registrationData)
    }

    override fun getOfferForTailor(tailorId: Long): Flow<List<OfferListItem>> {
        return remote.getOfferForTailor(tailorId)
    }

    override fun getOrdersForTailor(tailorId: Long): Flow<List<TransactionListItem>> {
        return remote.getOrdersForTailor(tailorId)
    }

    override fun getOfferDetails(offerListItem: OfferListItem): Flow<Offer> {
        return remote.getOfferDetails(offerListItem)
    }

    override fun getTransactionDetails(transactionListItem: TransactionListItem): Flow<Transaction> {
        return remote.getTransactionDetails(transactionListItem)
    }
}