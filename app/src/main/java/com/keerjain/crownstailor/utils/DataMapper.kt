package com.keerjain.crownstailor.utils

import com.keerjain.crownstailor.data.entities.detail.CustomerDetail
import com.keerjain.crownstailor.data.entities.detail.TailorCredentials
import com.keerjain.crownstailor.data.entities.detail.TailorSession
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import com.keerjain.crownstailor.data.entities.offer.OfferPrices
import com.keerjain.crownstailor.data.sources.remote.posts.ListIdBajuItem
import com.keerjain.crownstailor.data.sources.remote.posts.RegistrationData
import com.keerjain.crownstailor.data.entities.product.Product
import com.keerjain.crownstailor.data.sources.remote.posts.IsiHargaPost
import com.keerjain.crownstailor.data.sources.remote.responses.CatalogResponse
import com.keerjain.crownstailor.data.sources.remote.responses.DataItem
import com.keerjain.crownstailor.data.sources.remote.responses.UserResponse
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.PesananBaju

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

    fun mapOfferPriceToPostHarga(input: OfferPrices) = IsiHargaPost(
        idPesanan = input.idPesanan,
        biayaJahit = input.biayaJahit,
        biayaJemput = input.biayaJemput,
        biayaKirim = input.biayaKirim,
        biayaMaterial = input.biayaMaterial
    )

    fun mapCatalogResponseListToProductList(input: List<DataItem>) = input.map {
        it.id?.let { it1 ->
            Product(
                id = it1,
                productName = it.nama.toString()
            )
        }
    }

    fun mapUserResponseToCustomerDetail(id: Long, input: UserResponse) = CustomerDetail(
        userId = id,
        username = input.username.toString(),
        email = input.email.toString(),
    )
}