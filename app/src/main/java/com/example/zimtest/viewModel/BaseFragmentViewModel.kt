package com.example.zimtest.viewModel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.zimtest.R
import com.example.zimtest.getStringFromResources
import com.example.zimtest.model.DataResponse

import com.example.zimtest.model.repository.DataRepositoryImpl
import com.example.zimtest.network.Controller
import kotlinx.coroutines.*
import java.lang.Exception

class BaseFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: DataRepositoryImpl
    private var dataResponse: DataResponse
    private var dataResponseLive = MutableLiveData<DataResponse>()
    var errorMessage = ObservableField<String>("")


    init {
        dataResponse = DataResponse()
        val controller = Controller()
        repository = DataRepositoryImpl(controller)
    }

    fun getData(query: String): MutableLiveData<DataResponse> {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    dataResponse = repository.getData(query)
                    saveDataResponse()
                }catch (e:Exception){
                    setErrorMessage(getApplication<Application>().getStringFromResources(R.string.no_internet))
                }
            }
        }
        return dataResponseLive
    }

    private fun saveDataResponse() {
        if(dataResponse.data.isNotEmpty()){
            dataResponseLive.postValue(dataResponse)
        }else{
            setErrorMessage(getApplication<Application>().getStringFromResources(R.string.empty_response))
        }
    }

    private fun setErrorMessage(message: String){
        errorMessage.set(message)
    }
}