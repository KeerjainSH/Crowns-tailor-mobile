package com.keerjain.crownstailor.data.sources.remote.api

import com.keerjain.crownstailor.data.sources.remote.posts.IdPesananPost
import com.keerjain.crownstailor.data.sources.remote.posts.IsiHargaPost
import com.keerjain.crownstailor.data.sources.remote.responses.DetailPenjahitResponse
import com.keerjain.crownstailor.data.sources.remote.responses.PesananDetailResponse
import com.keerjain.crownstailor.data.sources.remote.responses.PesananResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("penjahit/{id}/pesanan")
    fun getAllOrders(
        @Path("id") id: Int,
    ) : PesananResponse

    @GET("penjahit/{id}")
    fun getPenjahitDetails(
        @Path("id") id: Int,
    ) : DetailPenjahitResponse

    @POST("pesanan/updateHarga")
    suspend fun fillPrice(
        @Body data: IsiHargaPost
    )

    @POST("pesanan/terimaTawar")
    suspend fun acceptOffer(
        @Body data: IdPesananPost
    )

    @POST("pesanan/tolakTawar")
    suspend fun declineOffer(
        @Body data: IdPesananPost
    )

    @GET("pesanan/{id}")
    fun getOrderDetails(
        @Path("id") id: Int,
    ) : PesananDetailResponse
}