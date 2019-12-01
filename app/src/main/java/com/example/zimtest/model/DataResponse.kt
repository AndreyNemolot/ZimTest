package com.example.zimtest.model

import java.util.ArrayList

data class DataResponse(
    val data: List<Data> = ArrayList(),
    val message: String = ""
)