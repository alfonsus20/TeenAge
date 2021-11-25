package com.example.teenage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val myDB = SQLiteHelper(this)
        val cursor = myDB.getUsers()

        Handler().postDelayed({
            val intent: Intent

            if (cursor.count == 0) {
                intent = Intent(this@SplashActivity, WelcomeActivity::class.java)
            } else {
                intent = Intent(this@SplashActivity, MainActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 3000)
    }
}