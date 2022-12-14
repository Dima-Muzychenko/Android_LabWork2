package com.example.test

import android.app.IntentService
import android.content.Intent
import kotlin.properties.Delegates

class SaveIntoFileService: IntentService(SaveIntoFileService::class.java.simpleName){

    var points by Delegates.notNull<Int>()
    override fun onHandleIntent(intent: Intent?) {
        var action:String = intent?.action!!
        if (action == ACTION_WRITE_TO_FILE){
            var points:Int=intent.getIntExtra(POINTS,0)//отримуємо бали
            writeToFile(points)
        }
    }

    private fun writeToFile(points: Int) {//запис в файл (дату ще записати)
        println("writing into file + $points")
    }

    companion object{

        val POINTS:String="POINTS"
        val ACTION_WRITE_TO_FILE:String="WRITE_TO_FILE"
    }
}