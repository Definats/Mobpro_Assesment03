package com.defina.projectmini03.network

import com.defina.projectmini03.model.OpStatus
import com.defina.projectmini03.model.Peminjaman
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

private const val BASE_URL = "https://api-mobpro-production-pina.up.railway.app/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ListApiSevice {
    @GET("peminjaman")
    suspend fun getList(
        @Header("Authorization") userId: String
    ): List<Peminjaman>

    @Multipart
    @POST("peminjaman/store")
    suspend fun postPeminjaman(
        @Header("Authorization") userId: String,
        @Part("nama") nama: RequestBody,
        @Part gambar: MultipartBody.Part
    ): OpStatus

    @DELETE("peminjaman/delete/{id}")
    suspend fun deletePeminjaman(
        @Header("Authorization") userId: String,
        @Path("id") id: String
    ):OpStatus

    @Multipart
    @POST("peminjaman/edit/{id}")
    suspend fun editPeminjaman(
        @Header("Authorization") userId: String,
        @Path("id") id: String,
        @Part("nama") nama: RequestBody,
        @Part gambar: MultipartBody.Part
    ): OpStatus
}


object ListApi {
    val service: ListApiSevice by lazy {
        retrofit.create(ListApiSevice::class.java)
    }
    fun getListUrl(gambar: String): String{
        return "https://api-mobpro-production-pina.up.railway.app/storage/$gambar"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }