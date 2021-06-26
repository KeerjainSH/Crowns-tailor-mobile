package com.keerjain.crownstailor.utils

import com.keerjain.crownstailor.data.entities.detail.TailorCredentials
import com.keerjain.crownstailor.data.entities.detail.TailorSession
import com.keerjain.crownstailor.data.entities.post.ListIdBajuItem
import com.keerjain.crownstailor.data.entities.post.RegistrationData
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
    )

    fun mapCredentialsToSessionData(input: TailorCredentials) = TailorSession(
        username = input.username.toString(),
        name = input.username.toString()
    )
}