package com.example.nasaapptern.service

import com.example.nasaapptern.model.Data
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface APIService {
    //https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=9UzZ4NfaBDTUcDPZ0jToVCkv5JyKehcFyaKi4bGn
    @GET("rovers/curiosity/photos?sol=1000&api_key=9UzZ4NfaBDTUcDPZ0jToVCkv5JyKehcFyaKi4bGn")
    suspend fun getData(): Response<Data>



}