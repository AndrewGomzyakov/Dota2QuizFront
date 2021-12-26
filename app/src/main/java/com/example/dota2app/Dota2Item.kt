package com.example.dota2app

import com.google.gson.annotations.SerializedName

data class Dota2Item(
    @SerializedName("itemId")
    val itemId: String,

    @SerializedName("name")
    val name: String?,

    @SerializedName("icon")
    val icon: String?,

    @SerializedName("price")
    val price: Int?,

    @SerializedName("stats")
    val stats: List<String>?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("cooldowm")
    val cooldown: Int?,

    @SerializedName("manacost")
    val manacost: Int?,

)