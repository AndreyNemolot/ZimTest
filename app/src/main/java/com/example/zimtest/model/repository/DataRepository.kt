package com.example.zimtest.model.repository

interface DataRepository<T> {
    fun getData(query: String, dataReadyCallback: DataReadyCallback<T>)
}