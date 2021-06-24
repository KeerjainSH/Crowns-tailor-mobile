package com.keerjain.crownstailor.viewmodels

import androidx.lifecycle.ViewModel
import com.keerjain.crownstailor.data.AppRepository

class OrderViewModel(private val repos: AppRepository) : ViewModel() {
    fun getOrders(tailorId: Long) = repos.getOrdersForTailor(tailorId)
}