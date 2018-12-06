package com.example.changjameslee.worldmovie

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager

class Splash : AppCompatActivity() {
    private val SPLASH_OUT = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        supportActionBar!!.hide()

        Handler().postDelayed({
            val homeIntent = Intent(this@Splash, LoginActivity::class.java)
            startActivity(homeIntent)
            finish()
        }, SPLASH_OUT.toLong())
    }
}
