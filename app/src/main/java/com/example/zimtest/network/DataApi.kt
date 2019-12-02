package com.example.zimtest.network

import com.example.zimtest.model.DataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DataApi {
    @GET("query")
    fun getData(
        @Query("query") query: String
    ): Call<DataResponse>

}