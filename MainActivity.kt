// MainActivity.kt
package com.example.wordle

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var wordToGuess: String
    private val guessHistory = mutableListOf<Pair<String, String>>()
    private var remainingChances = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editTextText)
        val button = findViewById<Button>(R.id.button)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)
        val guessHistoryTextView = findViewById<TextView>(R.id.guessHistoryTextView)
        val resetButton = findViewById<Button>(R.id.resetButton)

        // Set an OnClickListener for the "Guess" button
        button.setOnClickListener {
            if (remainingChances > 0) {
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

                // Decrement remaining chances
                remainingChances--
            } else {
                // Out of chances, display the correct word
                resultTextView.text = "Correct word: $wordToGuess"
                resultTextView.setTextColor(Color.RED) // Change text color to red

                // Disable the "Guess" button
                button.isEnabled = false

                // Display a message to reset the game
                Toast.makeText(this, "Out of chances. Please reset the game.", Toast.LENGTH_LONG).show()
            }
        }

        // Set an OnClickListener for the "Reset" button
        resetButton.setOnClickListener {
            // Clear the EditText
            editText.text.clear()

            // Clear the resultTextView
            resultTextView.text = ""
            resultTextView.setTextColor(Color.BLACK) // Reset text color to black

            // Clear the guessHistoryTextView
            guessHistoryTextView.text = ""

            // Generate a new word to guess
            wordToGuess = generateNewWordToGuess()

            // Reset the guess history
            guessHistory.clear()

            // Reset the remaining chances
            remainingChances = 3 // Change this to your initial number of chances

            // Enable the "Guess" button
            button.isEnabled = true
        }

        // Generate the initial word to guess
        wordToGuess = generateNewWordToGuess()
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

    private fun generateNewWordToGuess(): String {
        val wordList = "Area,Army,Baby,Back,Ball,Band,Bank,Base,Bill,Body,Book,Call,Card,Care,Case,Cash,City,Club,Cost,Date,Deal,Door,Duty,East,Edge,Face,Fact,Farm,Fear,File,Film,Fire,Firm,Fish,Food,Foot,Form,Fund,Game,Girl,Goal,Gold,Hair,Half,Hall,Hand,Head,Help,Hill,Home,Hope,Hour,Idea,Jack,John,Kind,King,Lack,Lady,Land,Life,Line,List,Look,Lord,Loss,Love,Mark,Mary,Mind,Miss,Move,Name,Need,News,Note,Page,Pain,Pair,Park,Part,Past,Path,Paul,Plan,Play,Post,Race,Rain,Rate,Rest,Rise,Risk,Road,Rock,Role,Room,Rule,Sale,Seat,Shop,Show,Side,Sign,Site,Size,Skin,Sort,Star,Step,Task,Team,Term,Test,Text,Time,Tour,Town,Tree,Turn,Type,Unit,User,View,Wall,Week,West,Wife,Will,Wind,Wine,Wood,Word,Work,Year,Bear,Beat,Blow,Burn,Call,Care,Cast,Come,Cook,Cope,Cost,Dare,Deal,Deny,Draw,Drop,Earn,Face,Fail,Fall,Fear,Feel,Fill,Find,Form,Gain,Give,Grow,Hang,Hate,Have,Head,Hear,Help,Hide,Hold,Hope,Hurt,Join,Jump,Keep,Kill,Know,Land,Last,Lead,Lend,Lift,Like,Link,Live,Look,Lose,Love,Make,Mark,Meet,Mind,Miss,Move,Must,Name,Need,Note,Open,Pass,Pick,Plan,Play,Pray,Pull,Push,Read,Rely,Rest,Ride,Ring,Rise,Risk,Roll,Rule,Save,Seek,Seem,Sell,Send,Shed,Show,Shut,Sign,Sing,Slip,Sort,Stay,Step,Stop,Suit,Take,Talk,Tell,Tend,Test,Turn,Vary,View,Vote,Wait,Wake,Walk,Want,Warn,Wash,Wear,Will,Wish,Work,Able,Back,Bare,Bass,Blue,Bold,Busy,Calm,Cold,Cool,Damp,Dark,Dead,Deaf,Dear,Deep,Dual,Dull,Dumb,Easy,Evil,Fair,Fast,Fine,Firm,Flat,Fond,Foul,Free,Full,Glad,Good,Grey,Grim,Half,Hard,Head,High,Holy,Huge,Just,Keen,Kind,Last,Late,Lazy,Like,Live,Lone,Long,Loud,Main,Male,Mass,Mean,Mere,Mild,Nazi,Near,Neat,Next,Nice,Okay,Only,Open,Oral,Pale,Past,Pink,Poor,Pure,Rare,Real,Rear,Rich,Rude,Safe,Same,Sick,Slim,Slow,Soft,Sole,Sore,Sure,Tall,Then,Thin,Tidy,Tiny,Tory,Ugly,Vain,Vast,Very,Vice,Warm,Wary,Weak,Wide,Wild,Wise,Zero,Ably,Afar,Anew,Away,Back,Dead,Deep,Down,Duly,Easy,Else,Even,Ever,Fair,Fast,Flat,Full,Good,Half,Hard,Here,High,Home,Idly,Just,Late,Like,Live,Long,Loud,Much,Near,Nice,Okay,Once,Only,Over,Part,Past,Real,Slow,Solo,Soon,Sure,That,Then,This,Thus,Very,When,Wide"
        val wordArray = wordList.split(",")

        val randomIndex = (0 until wordArray.size).random()
        return wordArray[randomIndex]
    }

}

