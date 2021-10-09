package com.gietuportalofficial.demoapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.gietuportalofficial.demoapp.MainActivity
import com.gietuportalofficial.demoapp.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        Handler(Looper.myLooper()!!).postDelayed({
            val intent=Intent(this,Login::class.java)
            startActivity(intent)
            finish()
        },1000)



    }
}