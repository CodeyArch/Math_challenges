package com.example.math_challenges

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class Level3Activity : AppCompatActivity() {
    private var score: Int = 0
    private var totalQuestions: Int = 0
    private var counter: Int = -1
    private lateinit var timer: CountDownTimer

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config: Configuration = resources.configuration
        if (config.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_level_3_tablet)
        } else {
            setContentView(R.layout.activity_level_3)
        }

        this.requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
        val timerTextView: TextView = findViewById(R.id.timeText)
        val totalTimerTextView: TextView = findViewById(R.id.timeText2)
        timer = object : CountDownTimer(10000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                counter++
                timerTextView.text = "Time remaining: ${-counter + 10}"
                totalTimerTextView.text = "Time: ${counter}s"
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                counter++
                timerTextView.text = "Times up!"
                cancel()
                startGameOver()
            }
        }.start()
        generateQuestionsUntilEnd(timer)
    }
    private fun startGameOver() {
        val prefs = getSharedPreferences("scoreGetter", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putInt("Score", score)
        editor.putInt("Time", counter)
        editor.putInt("Last Level", 3)
        editor.apply()
        val highScoreHardPrefs = getSharedPreferences("HighScore", Context.MODE_PRIVATE)
        val highScoreHardEditor: SharedPreferences.Editor = highScoreHardPrefs.edit()
        val bestScore = highScoreHardPrefs.getInt("Hard Best score", 0)
        val bestTime = highScoreHardPrefs.getInt("Hard Best time", 0)
        if (score > bestScore || score >= bestScore && bestTime >= counter || bestTime == 0) {
            highScoreHardEditor.putInt("Hard Best score", score)
            highScoreHardEditor.putInt("Hard Best time", counter)
            highScoreHardEditor.apply()
        }
        val gameOverIntent = Intent(this, GameOverActivity::class.java)
        startActivity(gameOverIntent)
    }
    @SuppressLint("SetTextI18n")
    private fun generateQuestionsUntilEnd(timer: CountDownTimer) {
        if (totalQuestions != 10) {
            totalQuestions += 1
            var answer = 0
            // var userAnswer: String
            when (Random.nextInt(1, 5)) {
                // Selecting a random question
                1 -> answer = addition()
                2 -> answer = subtraction()
                3 -> answer = multiplication()
                4 -> answer = division()
                else -> println("What")
            }
            val answerAsInt = answer
            val unshuffledList = mutableListOf(answerAsInt,
                answerAsInt + Random.nextInt(2, 5),
                answerAsInt + Random.nextInt(5, 10),
                if (answer > 1) {
                    answerAsInt - Random.nextInt(1, answer)
                } else {
                    answerAsInt + Random.nextInt(10, 16)
                })
            val shuffledList = unshuffledList.shuffled()
            val choice1 = findViewById<Button>(R.id.multipleChoice)
            val choice2 = findViewById<Button>(R.id.multipleChoice2)
            val choice3 = findViewById<Button>(R.id.multipleChoice3)
            val choice4 = findViewById<Button>(R.id.multipleChoice4)
            choice1.text = shuffledList[0].toString()
            choice2.text = shuffledList[1].toString()
            choice3.text = shuffledList[2].toString()
            choice4.text = shuffledList[3].toString()
            choice1.setOnClickListener {
                val choice = shuffledList[0]
                checkChoiceAgainstAnswer(answer, choice, timer)
            }
            choice2.setOnClickListener {
                val choice = shuffledList[1]
                checkChoiceAgainstAnswer(answer, choice, timer)
            }
            choice3.setOnClickListener {
                val choice = shuffledList[2]
                checkChoiceAgainstAnswer(answer, choice, timer)
            }
            choice4.setOnClickListener {
                val choice = shuffledList[3]
                checkChoiceAgainstAnswer(answer, choice, timer)
            }
        }
        else {
            timer.cancel()
            startGameOver()
            return
        }
    }
    @SuppressLint("SetTextI18n")
    fun checkChoiceAgainstAnswer (answer: Int, choice: Int, timer: CountDownTimer) {
        val mutedPref = getSharedPreferences("Muted", Context.MODE_PRIVATE)
        val mutedOn = mutedPref.getInt("Muted", 0)
        if (answer == choice) {
            score += 1
            val scoreView = findViewById<TextView>(R.id.score)
            scoreView.text = "Score: $score"
            if (mutedOn != 1) {
                val ring: MediaPlayer = MediaPlayer.create(this, R.raw.correctding)
                ring.start()
                ring.setOnCompletionListener {
                    ring.stop()
                    ring.reset()
                    ring.release()
                }
            }
        } else {
            if (mutedOn != 1) {
                val ring: MediaPlayer = MediaPlayer.create(this, R.raw.wrongbuzz)
                ring.start()
                ring.setOnCompletionListener {
                    ring.stop()
                    ring.reset()
                    ring.release()
                }
            }
        }
        generateQuestionsUntilEnd(timer)
    }
    @SuppressLint("SetTextI18n")
    fun addition(): Int {
        val (num1, num2) = generateRandomNums()
        val question = findViewById<TextView>(R.id.question)
        question.text = "$num1 + $num2 = ?"
        return (num1 + num2)
    }
    @SuppressLint("SetTextI18n")
    fun subtraction(): Int{
        var (num1, num2) = generateRandomNums()
        if (num2 > num1) {
            num2 = if (num1 - 1 == 0) {
                num1
            } else num1 - 1
        }
        val question = findViewById<TextView>(R.id.question)
        question.text = "$num1 - $num2 = ?"
        return (num1 - num2)
    }
    @SuppressLint("SetTextI18n")
    fun multiplication(): Int {
        val (num1, num2) = generateRandomNums()
        val question = findViewById<TextView>(R.id.question)
        question.text = "$num1 x $num2 = ?"
        return (num1 * num2)
    }
    @SuppressLint("SetTextI18n")
    fun division(): Int {
        var (num1, num2) = generateRandomNums()
        while (num1 % num2 != 0) {
            num1 = generateRandomNums()[0]
            num2 = generateRandomNums()[1]
        }
        val question = findViewById<TextView>(R.id.question)
        question.text = "$num1 / $num2 = ?"
        return (num1 / num2.toDouble()).toInt()
    }

    private fun generateRandomNums(): List<Int> {
        val num1 = Random.nextInt(1, 13)
        val num2 = Random.nextInt(1, 13)
        return listOf(num1, num2)
    }
    override fun finish() {
        super.finish()
        timer.cancel()
    }
    override fun onRestart() {
        super.onRestart()
        finish()
        timer.cancel()
        startActivity(intent)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        timer.cancel()
        finish()
    }
    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}