package com.example.math_challenges

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HighscoreActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config: Configuration = resources.configuration
        if (config.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_highscore_tablet)
        } else {
            setContentView(R.layout.activity_highscore)
        }

        val highScorePrefs = getSharedPreferences("HighScore", Context.MODE_PRIVATE)
        val easyBestScore = highScorePrefs.getInt("Easy Best score", 0)
        val easyBestTime = highScorePrefs.getInt("Easy Best time", 0)
        val mediumBestScore = highScorePrefs.getInt("Medium Best score", 0)
        val mediumBestTime = highScorePrefs.getInt("Medium Best time", 0)
        val hardBestScore = highScorePrefs.getInt("Hard Best score", 0)
        val hardBestTime = highScorePrefs.getInt("Hard Best time", 0)
        val easyScoreText = findViewById<TextView>(R.id.scoreEasyHighscore)
        val easyTimeText = findViewById<TextView>(R.id.timeEasyHighscore)
        val mediumScoreText = findViewById<TextView>(R.id.scoreMediumHighscore)
        val mediumTimeText = findViewById<TextView>(R.id.timeMediumHighscore)
        val hardScoreText = findViewById<TextView>(R.id.scoreHardHighscore)
        val hardTimeText = findViewById<TextView>(R.id.timeHardHighscore)
        easyScoreText.text = "Score: $easyBestScore out of 10"
        easyTimeText.text = "Time: $easyBestTime seconds"
        mediumScoreText.text = "Score: $mediumBestScore out of 10"
        mediumTimeText.text = "Time: $mediumBestTime seconds"
        hardScoreText.text = "Score: $hardBestScore out of 10"
        hardTimeText.text = "Time: $hardBestTime seconds"
        val mainMenu = findViewById<Button>(R.id.mainMenuFromHighscore)
        mainMenu.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}