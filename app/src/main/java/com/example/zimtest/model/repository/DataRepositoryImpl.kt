package com.example.zimtest.model.repository

import com.example.zimtest.await
import com.example.zimtest.model.DataResponse
import com.example.zimtest.network.Controller

class DataRepositoryImpl(private val controller: Controller):DataRepository{

    override suspend fun getData(query:String):DataResponse  =
        controller.getData(query).await()

}
