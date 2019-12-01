package com.example.zimtest.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DataApi {
    @GET("query")
    fun getUserAccount(
        @Query("query") query: String
    ): Call<com.example.zimtest.model.DataResponse>
}