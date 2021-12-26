package com.example.dota2app

import retrofit2.http.GET

interface ApiService {
    @GET("grids/0/0")
    suspend fun getItemsIds() : List<Dota2Item>
}