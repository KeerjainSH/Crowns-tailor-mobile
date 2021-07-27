package com.keerjain.crownstailor.utils

import com.keerjain.crownstailor.data.entities.detail.*
import com.keerjain.crownstailor.data.entities.offer.Offer
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import com.keerjain.crownstailor.data.entities.offer.OfferPrices
import com.keerjain.crownstailor.data.sources.remote.posts.ListIdBajuItem
import com.keerjain.crownstailor.data.sources.remote.posts.RegistrationData
import com.keerjain.crownstailor.data.entities.product.Product
import com.keerjain.crownstailor.data.sources.remote.posts.IsiHargaPost
import com.keerjain.crownstailor.data.sources.remote.responses.CatalogResponse
import com.keerjain.crownstailor.data.sources.remote.responses.DataItem
import com.keerjain.crownstailor.data.sources.remote.responses.UserResponse
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.DesignKustom
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.DetailJahit
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.DetailPesanan
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.PesananBaju
import com.keerjain.crownstailor.utils.enums.OfferStatus

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

    fun mapDetailJahitListToOrderDetailList(input: List<DetailJahit>) = input.map {
        OrderDetail(
            armSize = it.lengan!!.toFloat(),
            waistSize = it.pinggang!!.toFloat(),
            chestSize = it.dada!!.toFloat(),
            neckSize = it.leher!!.toFloat(),
            bodyHeight = it.tinggiTubuh!!.toFloat(),
            bodyWeight = it.beratBadan!!.toFloat(),
            instructions = it.instruksiPembuatan.toString()
        )
    }

    fun mapDesignKustomListToDesignDetail(input: List<DesignKustom>) = input.map {
        DesignDetail(
            foto = it.foto.toString(),
            deskripsi = it.deskripsi.toString()
        )
    }

    fun mapStatusToOfferStatus(input: Int?): OfferStatus {
        return if (input == 1 || input == null) {
            OfferStatus.NEW_OFFER
        } else if (input == 2) (
            OfferStatus.PRICE_SENT
        ) else {
            OfferStatus.PRICE_ACCEPTED
        }
    }
}