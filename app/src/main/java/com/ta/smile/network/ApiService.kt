package com.ta.smile.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api")
    fun getDataFecesUrine(@Query("uid") uid: String?): Call<FecesUrineResponse>

}