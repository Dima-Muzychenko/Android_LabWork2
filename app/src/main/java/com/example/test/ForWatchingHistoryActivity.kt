package com.example.test

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.test.databinding.ActivityForWatchingHistoryBinding

class ForWatchingHistoryActivity : AppCompatActivity() {
    
private lateinit var binding: ActivityForWatchingHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     binding = ActivityForWatchingHistoryBinding.inflate(layoutInflater)
     setContentView(binding.root)
        val fragment:FirstFragment = FirstFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container_2222,fragment)//саме так потрібно. Не через біндінг
            .commit()

    }
    fun fin(){
        finish()
    }
    
}