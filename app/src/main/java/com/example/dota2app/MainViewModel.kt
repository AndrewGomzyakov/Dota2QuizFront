package com.example.dota2app

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val dota2ItemsFirstColumn = ObservableArrayList<Dota2EntityViewModel>()
    val dota2ItemsSecondColumn = ObservableArrayList<Dota2EntityViewModel>()
    val dota2ItemsThirdColumn = ObservableArrayList<Dota2EntityViewModel>()
    val dota2ItemsFourthColumn = ObservableArrayList<Dota2EntityViewModel>()
    val dota2ItemsFifthColumn = ObservableArrayList<Dota2EntityViewModel>()

    var quiz: Quiz? = null

    val answer = ArrayList<String>()


    fun loadItems() {
        val retrofit = RetrofitClient.getClient().create(ApiService::class.java)

        viewModelScope.launch {

            var result = retrofit.getGridColumnItems(0)
            for(i in result){
                dota2ItemsFirstColumn.add(mapToViewModel(i))
            }
            result = retrofit.getGridColumnItems(1)
            for(i in result){
                dota2ItemsSecondColumn.add(mapToViewModel(i))
            }
            result = retrofit.getGridColumnItems(2)
            for(i in result){
                dota2ItemsThirdColumn.add(mapToViewModel(i))
            }
            result = retrofit.getGridColumnItems(3)
            for(i in result){
                dota2ItemsFourthColumn.add(mapToViewModel(i))
            }
            result = retrofit.getGridColumnItems(4)
            for(i in result){
                dota2ItemsFifthColumn.add(mapToViewModel(i))
            }
        }


    }

    fun getQuiz() {
        val retrofit = RetrofitClient.getClient().create(ApiService::class.java)
        viewModelScope.launch {
            quiz = retrofit.getQuiz()
        }
    }

    fun sendQuizAnswer() {
        val retrofit = RetrofitClient.getClient().create(ApiService::class.java)
        viewModelScope.launch {
//            val jsonObjectString = toJson(answer)
//            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
//
//            retrofit.sendQuizAnswer(quiz?.itemId, )
        }
    }

    private fun mapToViewModel(source : Dota2Item) : Dota2EntityViewModel {
        val imageBytes = Base64.decode(source.icon, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        val drawable = BitmapDrawable(decodedImage);
        return Dota2EntityViewModel(source.name!!, drawable)
    }

}