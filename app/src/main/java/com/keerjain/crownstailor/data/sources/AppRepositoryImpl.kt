package com.keerjain.crownstailor.data.sources

import com.keerjain.crownstailor.data.AppRepository
import com.keerjain.crownstailor.data.entities.detail.TailorCredentials
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem
import com.keerjain.crownstailor.data.sources.remote.RemoteDataSource
import com.keerjain.crownstailor.utils.SessionManager
import kotlinx.coroutines.flow.Flow

class AppRepositoryImpl(
    private val remote: RemoteDataSource,
    private val sessionManager: SessionManager
) : AppRepository {
    override fun signIn(tailor: TailorCredentials): Boolean {
        sessionManager.createLoginSession(tailor.username.toString())
        return true
    }

    override fun register(tailor: TailorCredentials): Boolean {
        val isRegistered = remote.registerUser(tailor)

        if (isRegistered) {
            sessionManager.createLoginSession(tailor.username.toString())
        }

        return isRegistered
    }

    override fun getOfferForTailor(tailorId: Long): Flow<List<OfferListItem>> {
        return remote.getOfferForTailor(tailorId)
    }

    override fun getOrdersForTailor(tailorId: Long): Flow<List<TransactionListItem>> {
        return remote.getOrdersForTailor(tailorId)
    }
}