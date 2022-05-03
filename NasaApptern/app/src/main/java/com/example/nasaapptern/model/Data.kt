package com.example.nasaapptern.model

import com.example.nasaapptern.service.APIService
import com.google.gson.annotations.SerializedName

class Data  {
    @SerializedName("photos")
    val photos:ArrayList<Photo>?=null
}