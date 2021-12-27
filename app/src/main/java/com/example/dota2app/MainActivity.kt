package com.example.dota2app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.dota2app.databinding.ActivityMainBinding
import me.tatarka.bindingcollectionadapter2.ItemBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = MainViewModel()
        binding.vm = viewModel
        binding.binding = ItemBinding.of { itemBinding, _, itemViewModel ->
            itemBinding.set(BR.item, R.layout.dota_2_item).bindExtra(BR.parent, viewModel)
        }
        viewModel.loadItems()

        viewModel.toastLiveData.observe(this) { text ->
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }

        super.onCreate(savedInstanceState)
    }

}