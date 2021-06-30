package com.keerjain.crownstailor.viewmodels

import androidx.lifecycle.ViewModel
import com.keerjain.crownstailor.data.AppRepository
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem

class OrderDetailViewModel(private val repos: AppRepository) : ViewModel() {
    fun getTransactionDetails(transactionListItem: TransactionListItem) =
        repos.getTransactionDetails(transactionListItem)

    fun startWorking() {

    }

    fun sendOrder() {

    }
}