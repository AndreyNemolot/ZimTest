package com.example.zimtest.model.repository

import com.example.zimtest.model.DataResponse
import com.example.zimtest.network.Controller
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DataRepositoryImpl(private val controller: Controller) : DataRepository<DataResponse> {
    override fun getData(query:String, dataReadyCallback: DataReadyCallback<DataResponse>) {
        controller.getData(query).enqueue { result ->
            when (result) {
                is Result.Success -> {
                    if (result.response.body() != null && result.response.isSuccessful) {
                        dataReadyCallback.dataReady(result.response.body()!!)
                    } else {
                        dataReadyCallback.dataFailure(DataResponse())
                    }
                }
                is Result.Failure -> {
                    dataReadyCallback.dataFailure(DataResponse())
                }
            }
        }
    }
}


private inline fun <reified T> Call<T>.enqueue(crossinline result: (Result<T>) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, error: Throwable) {
            result(Result.Failure(call, error))
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            result(Result.Success(call, response))
        }
    })
}

sealed class Result<T> {
    data class Success<T>(val call: Call<T>, val response: Response<T>) : Result<T>()
    data class Failure<T>(val call: Call<T>, val error: Throwable) : Result<T>()
}