package com.keerjain.crownstailor.viewmodels

import androidx.lifecycle.ViewModel
import com.keerjain.crownstailor.data.AppRepository
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import kotlinx.coroutines.flow.Flow

class OfferViewModel(private val repos: AppRepository) : ViewModel() {
    fun getOffers(): Flow<List<OfferListItem>> {
        return repos.getOfferForTailor()
    }
}