package com.keerjain.crownstailor.viewmodels

import androidx.lifecycle.ViewModel
import com.keerjain.crownstailor.data.AppRepository
import com.keerjain.crownstailor.data.entities.offer.OfferListItem

class OfferDetailViewModel(private val repos: AppRepository) : ViewModel() {
    fun getOfferDetail(offerListItem: OfferListItem) = repos.getOfferDetails(offerListItem)
}