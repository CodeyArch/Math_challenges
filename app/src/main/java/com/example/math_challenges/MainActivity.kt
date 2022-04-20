package com.example.math_challenges

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    private var isTablet = false
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config: Configuration = resources.configuration
        if (config.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_main_tablet)
            isTablet = true
        } else {
            setContentView(R.layout.activity_main)
        }
        this.requestedOrientation = SCREEN_ORIENTATION_PORTRAIT

        muteHandler()
        spinnerHandler()

        val howToPlayActivity = findViewById<Button>(R.id.howtoplay)
        howToPlayActivity.setOnClickListener {
            val intent = Intent(this, HowToPlayActivity::class.java)
            startActivity(intent)
        }
        val highscoreActivity = findViewById<ImageButton>(R.id.highScoreNav)
        highscoreActivity.setOnClickListener {
            val intent = Intent(this, HighscoreActivity::class.java)
            startActivity(intent)
        }
        val playQuiz = findViewById<Button>(R.id.play)
        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            val textToConfirmSelection = findViewById<TextView>(R.id.noSelection)
            @SuppressLint("SetTextI18n")
            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, i: Int, l: Long) {
                if (spinner.selectedItem.toString() == "Select Mode") {
                    playQuiz.setOnClickListener{
                        textToConfirmSelection.text = "Nothing selected"
                    }
                }
                if (spinner.selectedItem.toString() == "Easy") {
                    textToConfirmSelection.text = ""
                    playQuiz.setOnClickListener{
                        startMode("Easy")
                    }
                }
                if (spinner.selectedItem.toString() == "Medium") {
                    textToConfirmSelection.text = ""
                    playQuiz.setOnClickListener{
                        startMode("Medium")
                    }
                }
                if (spinner.selectedItem.toString() == "Hard") {
                    textToConfirmSelection.text = ""
                    playQuiz.setOnClickListener{
                        startMode("Hard")
                    }
                }
            }
            @SuppressLint("SetTextI18n")
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                playQuiz.setOnClickListener{
                    textToConfirmSelection.text = "Nothing selected"
                }
            }
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    fun startMode(mode: String) {
        if (mode == "Easy") {
            val playIntent = Intent(this, Level1Activity::class.java)
            startActivity(playIntent)
        }
        if (mode == "Medium") {
            val playIntent = Intent(this, Level2Activity::class.java)
            startActivity(playIntent)
        }
        if (mode == "Hard") {
            val playIntent = Intent(this, Level3Activity::class.java)
            startActivity(playIntent)
        }
    }

    private fun spinnerHandler() {
        val spinner = findViewById<Spinner>(R.id.spinner)
        val gameModes = arrayOf("Select Mode", "Easy", "Medium", "Hard")
        if (isTablet) {
            val arrayAdapter = ArrayAdapter(this, R.layout.spinner_list_tablet, gameModes)
            arrayAdapter.setDropDownViewResource(R.layout.spinner_list_tablet)
            spinner.adapter = arrayAdapter
        } else {
            val arrayAdapter = ArrayAdapter(this, R.layout.spinner_list, gameModes)
            arrayAdapter.setDropDownViewResource(R.layout.spinner_list)
            spinner.adapter = arrayAdapter
        }
    }
    private fun muteHandler() {
        var isMuted: Boolean
        val muteButton = findViewById<ImageButton>(R.id.muteButton)
        val mutedPref = getSharedPreferences("Muted", Context.MODE_PRIVATE)
        val mutedPrefEditor: SharedPreferences.Editor = mutedPref.edit()
        val mutedOn = mutedPref.getInt("Muted", 0)
        if (mutedOn == 1) {
            isMuted = true
            muteButton.setBackgroundResource(R.drawable.muted)
        } else {
            isMuted = false
        }
        muteButton.setOnClickListener {
            if (!isMuted) {
                isMuted = true
                muteButton.setBackgroundResource(R.drawable.muted)
                mutedPrefEditor.putInt("Muted", 1)
                mutedPrefEditor.apply()
            } else {
                isMuted = false
                muteButton.setBackgroundResource(R.drawable.unmuted)
                mutedPrefEditor.putInt("Muted", 0)
                mutedPrefEditor.apply()
            }
        }
    }
}