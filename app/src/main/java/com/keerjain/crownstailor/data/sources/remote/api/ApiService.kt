package com.keerjain.crownstailor.data.sources.remote.api

import com.keerjain.crownstailor.data.sources.remote.posts.IdPesananPost
import com.keerjain.crownstailor.data.sources.remote.posts.IsiHargaPost
import com.keerjain.crownstailor.data.sources.remote.posts.LoginPost
import com.keerjain.crownstailor.data.sources.remote.posts.RegistrationData
import com.keerjain.crownstailor.data.sources.remote.responses.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("penjahit/{id}")
    suspend fun getPenjahitDetails(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ) : Response<DetailPenjahitResponse>

    @POST("pesanan/updateHarga")
    suspend fun fillPrice(
        @Header("Authorization") token: String,
        @Body data: IsiHargaPost
    ) : Response<IsiHargaResponse>

    @POST("pesanan/terimaTawar")
    suspend fun acceptOffer(
        @Header("Authorization") token: String,
        @Body data: IdPesananPost
    ) : Response<StatusTawarResponse>

    @POST("pesanan/tolakTawar")
    suspend fun declineOffer(
        @Header("Authorization") token: String,
        @Body data: IdPesananPost
    ) : Response<StatusTawarResponse>

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

    @GET("pesanan/pembayaranBelumValid")
    suspend fun getOffers(
        @Header("Authorization") token: String,
    ) : Response<AllPesananResponse>

    @GET("pesanan/pembayaranValid")
    suspend fun getOrders(
        @Header("Authorization") token: String,
    ) : Response<AllPesananResponse>

    @GET("katalog")
    suspend fun getCatalog() : Response<CatalogResponse>

    @GET("pembeli/{id}")
    suspend fun getCustomerDetails(
        @Path("id") id: Long,
    ) : Response<CustomerDetailResponse>
}