package com.example.zimtest.model.repository

import com.example.zimtest.model.DataResponse

interface DataRepository {
    suspend fun getData(query: String): DataResponse
}