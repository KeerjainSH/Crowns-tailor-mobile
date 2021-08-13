package com.keerjain.crownstailor.utils

import com.keerjain.crownstailor.data.entities.detail.*
import com.keerjain.crownstailor.data.entities.offer.OfferPrices
import com.keerjain.crownstailor.data.entities.product.Product
import com.keerjain.crownstailor.data.entities.product.ProductListItem
import com.keerjain.crownstailor.data.sources.remote.posts.IsiHargaPost
import com.keerjain.crownstailor.data.sources.remote.posts.ListIdBajuItem
import com.keerjain.crownstailor.data.sources.remote.posts.ProfileUpdatePost
import com.keerjain.crownstailor.data.sources.remote.posts.RegistrationData
import com.keerjain.crownstailor.data.sources.remote.responses.DataItem
import com.keerjain.crownstailor.data.sources.remote.utils.entities.penjahit.DetailPenjahit
import com.keerjain.crownstailor.data.sources.remote.utils.entities.penjahit.KatalogItem
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.DesignKustom
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.DetailJahit
import com.keerjain.crownstailor.data.sources.remote.utils.entities.pesanan.LokasiPenjemputan
import com.keerjain.crownstailor.utils.enums.OfferStatus
import com.keerjain.crownstailor.utils.enums.Status

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
        biayaMaterial = input.biayaMaterial,
        hari = input.hari,
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
        return when (input) {
            null -> {
                OfferStatus.OFFER_NEW
            }
            1 -> (
                    OfferStatus.OFFER_RESPONSE_SENT
                    )
            2 -> {
                OfferStatus.OFFER_NEW_PRICE
            }
            else -> {
                OfferStatus.OFFER_ACCEPTED
            }
        }
    }

    fun mapStatusToOrderStatus(input: Int): Status {
        return if (input == 4) {
            Status.PAID_ORDER
        } else {
            Status.FINISHED
        }
    }

    fun mapOrderDetailsListToProductListItem(
        list: List<OrderDetail>,
        productDetail: ProductDetail
    ) = list.map {
        ProductListItem(
            productDetail = productDetail,
            orderDetail = it
        )
    }

    fun mapLokasiPenjemputanListToShipmentDetailList(
        input: List<LokasiPenjemputan>,
        name: String,
    ) = input.map {
        ShipmentDetail(
            receiverName = name,
            receiverAddress = "${it.alamat}, ${it.kecamatan}, ${it.kota}, ${it.provinsi} ${it.kodePos}",
            type = it.tipe!!,
        )
    }

    private fun mapKatalogItemListToListIdBajuItem(input: List<KatalogItem>) = input.map {
        ListIdBajuItem(
            idBaju = it.id
        )
    }

    fun mapDetailPenjahitToProfileUpdatePost(input: DetailPenjahit) =
        ProfileUpdatePost(
            kota = input.kota,
            noHp = input.noHp,
            listIdBaju = mapKatalogItemListToListIdBajuItem(input.katalog as List<KatalogItem>),
            noRekening = input.noRekening,
            namaPemilikRekening = input.namaRek,
            bank = input.bank,
            nama = input.nama,
            jenisKelamin = input.jenisKelamin,
            tanggalLahir = input.tanggalLahir,
            alamat = input.alamat,
            kecamatan = input.kecamatan,
            kodepos = input.kodepos,
            provinsi = input.provinsi,
        )
}