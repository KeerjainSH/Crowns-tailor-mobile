package com.keerjain.crownstailor.data

import com.keerjain.crownstailor.data.entities.detail.TailorCredentials
import com.keerjain.crownstailor.data.entities.offer.Offer
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import com.keerjain.crownstailor.data.entities.offer.OfferPrices
import com.keerjain.crownstailor.data.entities.product.Product
import com.keerjain.crownstailor.data.sources.remote.posts.RegistrationData
import com.keerjain.crownstailor.data.entities.transaction.Transaction
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.Penawaran
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun signIn(credentials: TailorCredentials): Flow<Boolean>
    fun register(registrationData: RegistrationData): Flow<Boolean>
    fun getOfferForTailor(): Flow<List<OfferListItem>>
    fun getOrdersForTailor(): Flow<List<TransactionListItem>>
    fun getOfferDetails(offerListItem: OfferListItem): Flow<Offer>
    fun getTransactionDetails(transactionListItem: TransactionListItem): Flow<Transaction>
    fun getCatalog(): Flow<List<Product>>
    fun acceptOffer(offer: Offer): Flow<Boolean>
    fun declineOffer(offer: Offer): Flow<Boolean>
    fun setPrice(prices: OfferPrices): Flow<Penawaran>
}