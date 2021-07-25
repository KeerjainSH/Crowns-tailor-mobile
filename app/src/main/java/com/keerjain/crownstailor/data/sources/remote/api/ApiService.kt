package com.keerjain.crownstailor.data.sources.remote.api

import com.keerjain.crownstailor.data.sources.remote.posts.IdPesananPost
import com.keerjain.crownstailor.data.sources.remote.posts.IsiHargaPost
import com.keerjain.crownstailor.data.sources.remote.posts.LoginPost
import com.keerjain.crownstailor.data.sources.remote.posts.RegistrationData
import com.keerjain.crownstailor.data.sources.remote.responses.DetailPenjahitResponse
import com.keerjain.crownstailor.data.sources.remote.responses.LoginResponse
import com.keerjain.crownstailor.data.sources.remote.responses.PesananDetailResponse
import com.keerjain.crownstailor.data.sources.remote.responses.PesananResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("penjahit/{id}/pesanan")
    suspend fun getAllOrders(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ) : Response<PesananResponse>

    @GET("penjahit/{id}")
    suspend fun getPenjahitDetails(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ) : Response<DetailPenjahitResponse>

    @POST("pesanan/updateHarga")
    suspend fun fillPrice(
        @Header("Authorization") token: String,
        @Body data: IsiHargaPost
    )

    @POST("pesanan/terimaTawar")
    suspend fun acceptOffer(
        @Header("Authorization") token: String,
        @Body data: IdPesananPost
    )

    @POST("pesanan/tolakTawar")
    suspend fun declineOffer(
        @Header("Authorization") token: String,
        @Body data: IdPesananPost
    )

    @GET("pesanan/{id}")
    suspend fun getOrderDetails(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ) : Response<PesananDetailResponse>

    @POST("penjahit/login")
    suspend fun login(
        @Body data: LoginPost
    ) : Response<LoginResponse>

    @POST("penjahit/register")
    suspend fun register(
        @Body data: RegistrationData
    ) : Response<LoginResponse>
}