package com.keerjain.crownstailor.viewmodels

import androidx.lifecycle.ViewModel
import com.keerjain.crownstailor.data.AppRepository
import com.keerjain.crownstailor.data.entities.offer.Offer
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import com.keerjain.crownstailor.data.entities.offer.OfferPrices

class OfferDetailViewModel(private val repos: AppRepository) : ViewModel() {
    private lateinit var offer: Offer

    fun setOffer(offer: Offer) {
        this.offer = offer
    }

    fun getOffer(): Offer = offer

    fun getOfferDetail(offerListItem: OfferListItem) = repos.getOfferDetails(offerListItem)

    fun acceptOffer() = repos.acceptOffer(offer)

    fun declineOffer() = repos.declineOffer(offer)

    fun setPrices(prices: OfferPrices) = repos.setPrice(prices)
}