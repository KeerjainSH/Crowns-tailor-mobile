package com.keerjain.crownstailor.data

import com.keerjain.crownstailor.data.entities.detail.TailorCredentials
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun signIn(tailor: TailorCredentials): Boolean
    fun register(tailor: TailorCredentials): Boolean
    fun getOfferForTailor(tailorId: Long): Flow<List<OfferListItem>>
}