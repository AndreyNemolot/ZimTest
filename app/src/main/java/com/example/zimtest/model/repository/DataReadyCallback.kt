package com.example.zimtest.model.repository

interface DataReadyCallback<T> {
    fun dataReady(data: T)
    fun dataFailure(data: T)
}