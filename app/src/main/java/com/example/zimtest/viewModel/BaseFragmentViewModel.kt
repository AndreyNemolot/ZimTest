package com.example.zimtest.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zimtest.model.DataResponse
import com.example.zimtest.model.repository.DataReadyCallback
import com.example.zimtest.model.repository.DataRepository
import com.example.zimtest.model.repository.DataRepositoryImpl
import com.example.zimtest.network.Controller

open class BaseFragmentViewModel : ViewModel() {
    private val repository: DataRepository<DataResponse>
    var dataResponse: DataResponse
    private var dataResponseLive = MutableLiveData<DataResponse>()

    init {
        dataResponse = DataResponse()
        val controller = Controller()
        repository= DataRepositoryImpl(controller)
    }

    fun getData(query: String): MutableLiveData<DataResponse> {
        repository.getData(query, object : DataReadyCallback<DataResponse> {
            override fun dataReady(data: DataResponse) {
                saveDataResponse(data)
            }

            override fun dataFailure(data: DataResponse) {
                saveDataResponse(data)
            }

        })
        return dataResponseLive
    }

    private fun saveDataResponse(dataResponse: DataResponse){
        this.dataResponse=dataResponse
        dataResponseLive.postValue(this.dataResponse)
    }
}