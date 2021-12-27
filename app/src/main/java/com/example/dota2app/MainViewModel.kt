package com.example.dota2app

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody

class MainViewModel : ViewModel() {

    val dota2ItemsFirstColumn = ObservableArrayList<Dota2EntityViewModel>()
    val dota2ItemsSecondColumn = ObservableArrayList<Dota2EntityViewModel>()
    val dota2ItemsThirdColumn = ObservableArrayList<Dota2EntityViewModel>()
    val dota2ItemsFourthColumn = ObservableArrayList<Dota2EntityViewModel>()
    val dota2ItemsFifthColumn = ObservableArrayList<Dota2EntityViewModel>()

    var quiz: Quiz? = null

    //    var quizItem: Dota2EntityViewModel? = null
    val quizItem = ObservableArrayList<Dota2EntityViewModel>()

    val answer = ObservableArrayList<Dota2EntityViewModel>()

    val toastLiveData = MutableLiveData<String>()

    private val retrofit = RetrofitClient.getClient().create(ApiService::class.java)


    fun loadItems() {
        loadInto(dota2ItemsFirstColumn, 0)
        loadInto(dota2ItemsSecondColumn, 1)
        loadInto(dota2ItemsThirdColumn, 2)
        loadInto(dota2ItemsFourthColumn, 3)
        loadInto(dota2ItemsFifthColumn, 4)
    }

    fun loadInto(list: ObservableArrayList<Dota2EntityViewModel>, id: Int) {
        viewModelScope.launch {
            val result = retrofit.getGridColumnItems(id)
            for (i in result) {
                list.add(mapToViewModel(i))
            }
        }
    }

    fun startQuiz() {
        val retrofit = RetrofitClient.getClient().create(ApiService::class.java)
        answer.clear()
        viewModelScope.launch {
            val quiz = retrofit.getQuiz().also { quiz = it }
//            quizItem = mapToViewModel(retrofit.getItem(quiz.itemId))
            with(quizItem) {
                clear()
                add(mapToViewModel(retrofit.getItem(quiz.itemId)))
            }
        }
    }

    fun sendQuizAnswer() {
        quiz?.let { quiz ->
            if (answer.size == quiz.numOfItems + quiz.numOfRecipes) {
                val retrofit = RetrofitClient.getClient().create(ApiService::class.java)
                viewModelScope.launch {
                    val json = JsonArray()
                    for (i in answer) json.add(i.itemId)
                    val jsonObjectString = json.toString()
//                    val jsonObjectString = answer.forEach {json.add(it.itemId)}.toString()

                    val body = RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),
                        jsonObjectString
                    )

                    val isCorrectAnswer = retrofit.sendQuizAnswer(quiz.itemId, body)
                    answer.clear()
                    if (isCorrectAnswer) {
                        toastLiveData.postValue("Correct!!! Get new quiz!")
                    } else {
                        toastLiveData.postValue("Incorrect answer! Try again!")
                    }
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

    private fun mapToViewModel(source: Dota2Item): Dota2EntityViewModel {
        val imageBytes = Base64.decode(source.icon, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        val drawable = BitmapDrawable(decodedImage);
        return Dota2EntityViewModel(source.itemId, source.name!!, drawable)
    }

}