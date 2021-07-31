package com.keerjain.crownstailor.viewmodels

import androidx.lifecycle.ViewModel
import com.keerjain.crownstailor.data.AppRepository

class HomeViewModel(private val repos: AppRepository) : ViewModel() {
    fun getOrders() = repos.getOrdersForTailor()

    fun getOffers() = repos.getOfferForTailor()
}