package com.example.dota2app

import retrofit2.http.GET

interface ApiService {
    @GET("grids/0/0")
    suspend fun getFirstGridColumnItems() : List<Dota2Item>

    @GET("grids/0/1")
    suspend fun getSecondGridColumnItems() : List<Dota2Item>

}