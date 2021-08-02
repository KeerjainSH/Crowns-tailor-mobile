package com.keerjain.crownstailor.data.sources

import com.keerjain.crownstailor.data.AppRepository
import com.keerjain.crownstailor.data.entities.detail.TailorCredentials
import com.keerjain.crownstailor.data.entities.offer.Offer
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import com.keerjain.crownstailor.data.entities.offer.OfferPrices
import com.keerjain.crownstailor.data.entities.product.Product
import com.keerjain.crownstailor.data.sources.remote.posts.RegistrationData
import com.keerjain.crownstailor.data.entities.transaction.Transaction
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem
import com.keerjain.crownstailor.data.sources.remote.RemoteDataSource
import com.keerjain.crownstailor.data.sources.remote.posts.ProfileUpdatePost
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.Penawaran
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

    override fun getOfferForTailor(): Flow<List<OfferListItem>> {
        return remote.getOfferForTailor()
    }

    override fun getOrdersForTailor(): Flow<List<TransactionListItem>> {
        return remote.getOrdersForTailor()
    }

    override fun getOfferDetails(offerListItem: OfferListItem): Flow<Offer> {
        return remote.getOfferDetails(offerListItem)
    }

    override fun getTransactionDetails(transactionListItem: TransactionListItem): Flow<Transaction> {
        return remote.getTransactionDetails(transactionListItem)
    }

    override fun getCatalog(): Flow<List<Product>> {
        return remote.getCatalog()
    }

    override fun acceptOffer(offer: Offer): Flow<Boolean> {
        return remote.acceptOffer(offer.offerId.toInt())
    }

    override fun declineOffer(offer: Offer): Flow<Boolean> {
        return remote.declineOffer(offer.offerId.toInt())
    }

    override fun setPrice(prices: OfferPrices): Flow<Penawaran> {
        return remote.setPrice(prices)
    }

    override fun getTailorDetails(): Flow<ProfileUpdatePost> {
        return remote.getTailorDetails()
    }

    override fun updateTailorDetails(profile: ProfileUpdatePost): Flow<Boolean> {
        return remote.updateTailorProfile(profile)
    }

    override fun getRating(): Flow<Float> {
        return remote.getRating()
    }
}