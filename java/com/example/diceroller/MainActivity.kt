package com.example.diceroller

import android.app.Activity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random

class MainActivity : Activity() {

    private lateinit var diceImage: ImageView
    private lateinit var resultText: TextView
    private lateinit var historyText: TextView
    private lateinit var rollButton: Button

    private val rollHistory = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupRollButton()
    }

    private fun initializeViews() {
        diceImage = findViewById(R.id.ivDice)
        resultText = findViewById(R.id.tvResult)
        historyText = findViewById(R.id.tvHistory)
        rollButton = findViewById(R.id.btnRoll)
    }

    private fun setupRollButton() {
        rollButton.setOnClickListener {
            rollDice()
        }
    }

    /**
     * Mengacak angka dadu dari 1 sampai 6.
     */
    private fun rollDice() {
        rollButton.isEnabled = false

        val randomNumber = Random.nextInt(
            from = 1,
            until = 7
        )

        val animation = AnimationUtils.loadAnimation(
            this,
            R.anim.dice_roll
        )

        diceImage.startAnimation(animation)

        diceImage.postDelayed({
            showDiceResult(randomNumber)
            addToHistory(randomNumber)
            rollButton.isEnabled = true
        }, 350)
    }

    /**
     * Menggunakan when untuk memetakan angka random
     * ke gambar dadu yang sesuai.
     */
    private fun showDiceResult(number: Int) {

        val diceImageResource = when (number) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.dice_1
        }

        diceImage.setImageResource(diceImageResource)
        diceImage.contentDescription = "Dadu menunjukkan angka $number"
        resultText.text = "Hasil: $number"
    }

    /**
     * Menyimpan maksimal 10 hasil kocokan terakhir.
     */
    private fun addToHistory(result: Int) {
        rollHistory.add(0, result)

        if (rollHistory.size > 10) {
            rollHistory.removeAt(rollHistory.lastIndex)
        }

        historyText.text = rollHistory
            .mapIndexed { index, value ->
                "${index + 1}. Hasil kocokan: $value"
            }
            .joinToString(separator = "\n")
    }
}
