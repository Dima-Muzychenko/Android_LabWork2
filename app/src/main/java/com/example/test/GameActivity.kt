package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle

class GameActivity : AppCompatActivity() {
    var SIZE_OF_MATRIX: Int = 3
    var TIMER: Int=15
    var MIN_FREQUENCY: Float = 1f
    var MAX_FREQUENCY: Float = 1.5f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        SIZE_OF_MATRIX = intent.getIntExtra(size_of_matrix, 0)
        TIMER = intent.getIntExtra(timer, 0)
        MIN_FREQUENCY = intent.getFloatExtra(min_frequency, 0f)
        MAX_FREQUENCY = intent.getFloatExtra(max_frequency, 0f)
        println("size_of_matrix=$SIZE_OF_MATRIX, timer=$TIMER, frequency=$MIN_FREQUENCY, $MAX_FREQUENCY")

    }







    companion object{//константи
        val size_of_matrix: String = "size_of_matrix"
        val timer: String = "timer"
        val min_frequency: String = "min_frequency"
        val max_frequency: String = "max_frequency"
    }

//    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
//        super.onSaveInstanceState(outState, outPersistentState)
//    }
}