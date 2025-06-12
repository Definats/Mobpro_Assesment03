package com.defina.projectmini03.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api-mobpro-production-pina.up.railway.app/api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ListApiSevice {
    @GET("peminjaman")
    suspend fun getList(): String
}

object ListApi {
    val service: ListApiSevice by lazy {
        retrofit.create(ListApiSevice::class.java)
    }
}