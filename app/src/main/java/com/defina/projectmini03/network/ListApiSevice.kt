package com.defina.projectmini03.network

import com.defina.projectmini03.model.Peminjaman
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

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
    suspend fun getList(): List<Peminjaman>
}

object ListApi {
    val service: ListApiSevice by lazy {
        retrofit.create(ListApiSevice::class.java)
    }
    fun getListUrl(gambar: String): String{
        return "https://api-mobpro-production-pina.up.railway.app/storage/$gambar"
    }
}