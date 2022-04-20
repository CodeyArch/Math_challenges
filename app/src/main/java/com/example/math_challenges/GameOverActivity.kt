package com.example.math_challenges

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.xml.KonfettiView
import java.util.concurrent.TimeUnit


class GameOverActivity : AppCompatActivity() {
    private lateinit var viewKonfetti: KonfettiView
    @SuppressLint("SetTextI18n", "SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config: Configuration = resources.configuration
        if (config.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_game_over_tablet)
        } else {
            setContentView(R.layout.activity_game_over)
        }
        this.requestedOrientation = SCREEN_ORIENTATION_PORTRAIT

        val mutedPref = getSharedPreferences("Muted", Context.MODE_PRIVATE)
        val mutedOn = mutedPref.getInt("Muted", 0)
        if (mutedOn != 1) {
            val ring: MediaPlayer = MediaPlayer.create(this, R.raw.quizwin)
            ring.start()
            ring.setOnCompletionListener {
                ring.stop()
                ring.reset()
                ring.release()
            }
        }
        viewKonfetti = findViewById(R.id.konfettiView)
        val party = Party(
            speed = 0f,
            maxSpeed = 40f,
            damping = 0.9f,
            spread = 360,
            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
            emitter = Emitter(duration = 1, TimeUnit.SECONDS).max(250),
            position = Position.Relative(0.5, 0.1)
        )
        viewKonfetti.start(party)

        val prefs = getSharedPreferences("scoreGetter", Context.MODE_PRIVATE)
        val score = prefs.getInt("Score", 0)
        val time = prefs.getInt("Time", 0)
        val scoreView = findViewById<TextView>(R.id.scoreGameOver)
        val timeView = findViewById<TextView>(R.id.timeGameOver)
        scoreView.text = "Score: $score out of 10"
        timeView.text = "Time: $time seconds"
        val mainMenu = findViewById<Button>(R.id.mainMenuFromGameOver)
        val playAgain = findViewById<Button>(R.id.playAgain)
        mainMenu.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        playAgain.setOnClickListener {
            this.finish()
        }
    }
}