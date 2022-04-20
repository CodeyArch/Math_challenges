package com.example.math_challenges

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HowToPlayActivity : AppCompatActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config: Configuration = resources.configuration
        if (config.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_how_to_play_tablet)
        } else {
            setContentView(R.layout.activity_how_to_play)
        }

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val mainMenu = findViewById<Button>(R.id.mainMenu)
        mainMenu.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}