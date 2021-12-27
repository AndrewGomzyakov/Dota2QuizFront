package com.example.dota2app

import com.google.gson.annotations.SerializedName

data class Quiz(
    @SerializedName("itemId")
    val itemId: String,

    @SerializedName("numOfItems")
    val numOfItems: Int?,

    @SerializedName("numOfRecipes")
    val numOfRecipes: Int?,
)