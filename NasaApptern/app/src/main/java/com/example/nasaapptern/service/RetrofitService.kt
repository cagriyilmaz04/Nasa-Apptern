package com.example.nasaapptern.service

import com.example.nasaapptern.model.Data
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
           private val retroift= Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/mars-photos/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService::class.java)

        suspend fun AllData():Response<Data>{
            return retroift.getData()

        }




}