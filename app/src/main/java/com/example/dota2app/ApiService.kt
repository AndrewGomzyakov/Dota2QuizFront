package com.example.dota2app

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("grids/0/{id}")
    suspend fun getGridColumnItems(@Path("id") id: Int?) : List<Dota2Item>

    @GET("quiz")
    suspend fun getQuiz() : Quiz

    @GET("items/{itemId}")
    suspend fun getItem(@Path("itemId") itemId: String) : Dota2Item

    @POST("quiz/{itemId}")
    suspend fun sendQuizAnswer(@Path("itemId") itemId: String?, @Body requestBody: RequestBody) : Boolean
}