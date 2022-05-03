package com.example.nasaapptern.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapptern.model.Data
import kotlinx.coroutines.*
import retrofit2.Response

class MainViewModel :ViewModel() {
    val dataList=MutableLiveData<Data>()
    val errorMessage = MutableLiveData<String>()
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    var job:Job?=null
    val RetrofitService=com.example.nasaapptern.service.RetrofitService()
    fun getAllDatas(){
        job= CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
                val response=RetrofitService.AllData()
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    dataList.postValue(response.body()!!)

                }else{
                    onError("Error : ${response.message()}")
                }

            }
        }

    }
    private fun onError(message: String) {
        errorMessage.value = message

    }

    override fun onCleared() {
        super.onCleared()
        job=null
    }

}