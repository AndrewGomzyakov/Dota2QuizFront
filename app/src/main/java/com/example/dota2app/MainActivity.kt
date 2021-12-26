package com.example.dota2app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.dota2app.databinding.ActivityMainBinding
import me.tatarka.bindingcollectionadapter2.ItemBinding

class MainActivity : AppCompatActivity() {

    val viewModel = MainViewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = viewModel
        binding.binding = ItemBinding.of { itemBinding, _, itemViewModel ->
            itemBinding.set(BR.item, R.layout.dota_2_item).bindExtra(BR.parent, viewModel)
        }
        viewModel.loadItems()
        super.onCreate(savedInstanceState)
    }

}