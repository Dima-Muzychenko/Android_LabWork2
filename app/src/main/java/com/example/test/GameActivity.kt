package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
    }

    fun checkGit(){
        println("Git?")
    }









<<<<<<< HEAD


    //потім реалізувати зберігання стану
=======
    // потім реалізувати зберігання стану
>>>>>>> a701735 (hey)
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }
}