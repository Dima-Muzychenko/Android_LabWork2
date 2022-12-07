package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.example.test.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity(){

    private lateinit var binding: ActivitySplashScreenBinding
    var handler: Handler = Handler(Looper.getMainLooper())
    var isClicked: Boolean = false//показує, чи ми натиснули на екран раніше, ніж закінчився таймер

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        handler.postDelayed({
            if(!isClicked) {
                val intent = Intent(this, ParametersActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000) // 3000 is the delayed time in milliseconds.
        //при натисканні на екран
        binding.splashScreenID.setOnClickListener { closeSplashScreen() }

    }
    private fun closeSplashScreen() {
            val intent = Intent(this, ParametersActivity::class.java)
            startActivity(intent)
            finish()
            isClicked=true
    }




}