package com.example.nasaapptern.model

import com.google.gson.annotations.SerializedName

class Photo {
    @SerializedName("id")
    val id:Int?=null
    @SerializedName("sol")
    val sol:Int?=null
    @SerializedName("camera")
    val camera:Camera?=null
    @SerializedName("rover")
    val rover:Rover?=null
    @SerializedName("img_src")
    val img_src:String?=null
    @SerializedName("earth_date")
    val earth_date:String?=null

}