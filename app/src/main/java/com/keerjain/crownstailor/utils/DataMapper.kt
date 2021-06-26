package com.keerjain.crownstailor.utils

import com.keerjain.crownstailor.data.entities.post.ListIdBajuItem
import com.keerjain.crownstailor.data.entities.product.Product

object DataMapper {
    fun mapProductListToProductIdList(input: List<Product>) = input.map {
        ListIdBajuItem(
            it.id
        )
    }
}