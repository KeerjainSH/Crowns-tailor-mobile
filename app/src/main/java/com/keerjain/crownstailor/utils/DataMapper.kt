package com.keerjain.crownstailor.utils

import com.keerjain.crownstailor.data.entities.detail.TailorCredentials
import com.keerjain.crownstailor.data.entities.detail.TailorSession
import com.keerjain.crownstailor.data.sources.remote.posts.ListIdBajuItem
import com.keerjain.crownstailor.data.sources.remote.posts.RegistrationData
import com.keerjain.crownstailor.data.entities.product.Product

object DataMapper {
    fun mapProductListToProductIdList(input: List<Product>) = input.map {
        ListIdBajuItem(
            it.id
        )
    }

    fun mapRegistrationDataToSessionData(input: RegistrationData) = TailorSession(
        username = input.username.toString(),
        name = input.nama.toString(),
        userId = null,
        token = null,
    )

    fun mapCredentialsToSessionData(input: TailorCredentials) = TailorSession(
        username = input.username.toString(),
        name = input.username.toString(),
        userId = null,
        token = null,
    )
}