package com.example.dota2app

import android.app.Application
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val dota2ItemsFirstColumn = ObservableArrayList<Dota2EntityViewModel>()
    val dota2ItemsSecondColumn = ObservableArrayList<Dota2EntityViewModel>()
    val dota2ItemsThirdColumn = ObservableArrayList<Dota2EntityViewModel>()
    val dota2ItemsFourthColumn = ObservableArrayList<Dota2EntityViewModel>()
    val dota2ItemsFifthColumn = ObservableArrayList<Dota2EntityViewModel>()

    var quiz: Quiz? = null
    var quizItem: Dota2EntityViewModel? = null

    val answer = ObservableArrayList<Dota2EntityViewModel>()


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

    fun startQuiz() {
        val retrofit = RetrofitClient.getClient().create(ApiService::class.java)
        answer.clear()
        viewModelScope.launch {
            quiz = retrofit.getQuiz()
            quizItem = mapToViewModel(retrofit.getItem(quiz!!.itemId))
            var a = quiz
        }
    }

    fun sendQuizAnswer() {
        if (quiz != null && answer.size == quiz!!.numOfItems + quiz!!.numOfRecipes) {
            val retrofit = RetrofitClient.getClient().create(ApiService::class.java)
            viewModelScope.launch {
                val a = JsonArray()
                for (i in answer) {
                    a.add(i.itemId)
                }
                a.toString()
                val jsonObjectString = a.toString()

                val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObjectString)

                val isCorrectAnswer = retrofit.sendQuizAnswer(quiz?.itemId, body )
                answer.clear()
                if (isCorrectAnswer) {
                    Toast.makeText(
                        getApplication<Application>().applicationContext,
                        "Correct!!! Get new quiz!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        getApplication<Application>().applicationContext,
                        "Incorrect answer! Try again!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }


        }
    }

    fun itemClick(itemId: Dota2EntityViewModel) {
        if (quiz != null) {
            answer.add(itemId)
            sendQuizAnswer()
        }
    }

    private fun mapToViewModel(source : Dota2Item) : Dota2EntityViewModel {
        val imageBytes = Base64.decode(source.icon, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        val drawable = BitmapDrawable(decodedImage);
        return Dota2EntityViewModel(source.itemId, source.name!!, drawable)
    }

}