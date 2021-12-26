package com.example.dota2app

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val dota2Items = ObservableArrayList<Dota2EntityViewModel>()

    fun loadItems() {
        val retrofit = RetrofitClient.getClient().create(ApiService::class.java)

        viewModelScope.launch {

            val result = retrofit.getItemsIds()
            dota2Items.add(mapToEntity(result[0]))
        }

    }

    fun mapToEntity(source : Dota2Item) : Dota2EntityViewModel {
        val imageBytes = Base64.decode(source.icon, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        val drawable = BitmapDrawable(decodedImage);
        return Dota2EntityViewModel(source.name!!, drawable)
    }

}