package com.example.test

import android.content.Intent
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
private var close:Boolean=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     binding = ActivityForWatchingHistoryBinding.inflate(layoutInflater)
     setContentView(binding.root)
//        var intent: Intent = Intent(this, SaveIntoFileService::class.java)
//        intent.setAction(SaveIntoFileService.ACTION_RESTORE_FROM_FILE)
//        startService(intent)
        val fragment:FirstFragment = FirstFragment.newInstance()


        supportFragmentManager
            .beginTransaction()
            .add(R.id.container_2222,fragment)//саме так потрібно. Не через біндінг
            .commit()

    }

    
}