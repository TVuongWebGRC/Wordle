// MainActivity.kt
package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var wordToGuess: String
    private val guessHistory = mutableListOf<Pair<String, String>>() // Pair of guess and check result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editTextText)
        val button = findViewById<Button>(R.id.button)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)
        val guessHistoryTextView = findViewById<TextView>(R.id.guessHistoryTextView)

        // Set an OnClickListener for the "Guess" button
        button.setOnClickListener {
            val userGuess = editText.text.toString()
            val result = checkGuess(userGuess)

            // Display the result to the user
            resultTextView.text = result

            // Update the guess history
            val guessCheckPair = Pair(userGuess, result)
            guessHistory.add(guessCheckPair)

            // Display the guess history in the desired format
            val guessHistoryText = buildGuessHistoryText(guessHistory)
            guessHistoryTextView.text = guessHistoryText

            // Clear the EditText
            editText.text.clear()

            // Check if the user has guessed the correct word
            if (result == "OOOO") {
                Toast.makeText(this, "Congratulations! You guessed the word!", Toast.LENGTH_LONG).show()
            }
        }

        wordToGuess = "Guess #1"
    }

    private fun checkGuess(guess: String): String {
        var result = ""
        for (i in 0 until minOf(guess.length, wordToGuess.length)) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            } else if (guess[i] in wordToGuess) {
                result += "+"
            } else {
                result += "X"
            }
        }
        return result
    }

    private fun buildGuessHistoryText(history: List<Pair<String, String>>): String {
        val builder = StringBuilder()
        for ((index, pair) in history.withIndex()) {
            val guessNumber = index + 1
            val guess = pair.first
            val checkResult = pair.second
            builder.append("Guess #$guessNumber\t$guess\nGuess #$guessNumber Check\t$checkResult\n")
        }
        return builder.toString()
    }
}
