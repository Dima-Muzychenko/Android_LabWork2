package com.example.test

import android.app.IntentService
import android.content.Context
import android.content.Intent
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class SaveIntoFileService: IntentService(SaveIntoFileService::class.java.simpleName){


    override fun onHandleIntent(intent: Intent?) {
        var action:String = intent?.action!!
        if (action == ACTION_WRITE_TO_FILE){
            var points:Int=intent.getIntExtra(POINTS,0)//отримуємо бали
            writeToFile(points)
        }else if (action == ACTION_RESTORE_FROM_FILE){
            toRestoreFile()
        }
    }

    private fun writeToFile(points: Int) {//запис в файл (дату ще записати)
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val current = formatter.format(time)
        val text = "\nAttempt of $current. \nPoints: $points"
        val fileOutputStream:FileOutputStream = openFileOutput(FILE_NAME, Context.MODE_APPEND)
        fileOutputStream.write(text.toByteArray())
        fileOutputStream.close()

        println("writing into file + $points")
    }
    private fun toRestoreFile(){
        var fileInputStream: FileInputStream? = openFileInput(FILE_NAME)
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while (run {
                text = bufferedReader.readLine()
                text
            } != null) {
            stringBuilder.append(text+"\n")
        }
        stringBuilder.append("\n")

        var obs:Observe = application as Observe
        obs.ReadFromFile(stringBuilder.toString())//передаємо прочитаний текст з файлу слухачу

    }

    companion object{
        val FILE_NAME: String = "results.txt"
        val POINTS:String="POINTS"
        val ACTION_WRITE_TO_FILE:String="WRITE_TO_FILE"
        val ACTION_RESTORE_FROM_FILE:String="RESTORE_FROM_FILE"
    }
}