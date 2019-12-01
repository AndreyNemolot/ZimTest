package com.example.zimtest.network


import com.example.zimtest.model.DataResponse
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Controller {
    private val BASE_URL_ACCOUNT = "https://kot3.com/xim/api.php/"
    private var accountRetrofit: Retrofit

    init {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        accountRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_ACCOUNT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun getData(query: String): Call<DataResponse> {
        val accountApi = accountRetrofit.create(DataApi::class.java)
        return accountApi.getUserAccount(query)
    }
}