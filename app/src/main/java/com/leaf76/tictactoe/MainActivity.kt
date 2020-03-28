package com.leaf76.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"

    //create two dimension buttons use  list array
    private var buttons = arrayOf<ArrayList<Button>>(arrayListOf(),arrayListOf(),arrayListOf())

    private var player1Turn: Boolean = true

    private var roundCount: Int = 0

    private var player1Points: Int = 0
    private var player2Points: Int = 0

    private lateinit var textViewPlayer1: TextView
    private lateinit var textViewPlayer2: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewPlayer1 = text_view_p1
        textViewPlayer2 = text_view_p2

        for (i in 0 until 3) {
            for (j in 0 until 3) {
                val buttonId = "button_$i$j"
                Log.i(TAG, "Get button ID: $buttonId")
                val resId = resources.getIdentifier(buttonId, "id", packageName)

                Log.i(TAG, "Get resource ID: $resId")

                buttons[i].add( findViewById(resId))

                Log.i(TAG, "Get buttons text: ${buttons[i][j].toString()}")

                buttons[i][j].setOnClickListener { v ->

                    if ((v as Button).text.toString().isNotEmpty()) {
                        return@setOnClickListener
                    }

                    if (player1Turn) {
                        v.text = "X"
                    } else {
                        v.text = "O"
                    }

                    Log.i(
                        TAG,
                        "Get Button row:$i column:$j and resource ID: $resId got ${buttons[i][j].text} "
                    )

                    roundCount++

                    if (checkForWin()) {
                        if (player1Turn) {
                            player1Wins()
                        } else {
                            player2Wins()
                        }
                    } else if (roundCount == 9) {
                        draw()
                    } else {
                        player1Turn = !player1Turn
                    }
                }
            }
        }
        val buttonReset = button_reset
        buttonReset.setOnClickListener {

        }
    }

    private fun draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show()
        resetBoard()
    }

    private fun player1Wins() {
        player1Points++
        Toast.makeText(this, "Player 1 Wins", Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    private fun player2Wins() {
        player2Points++
        Toast.makeText(this, "Player 2 Wins", Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    private fun updatePointsText() {
        textViewPlayer1.text = "Player 1: $player1Points"
        textViewPlayer2.text = "Player 2: $player2Points"
    }

    private fun resetBoard() {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                buttons[i][j].text = ""
            }
        }

        roundCount = 0
        player1Turn = true
    }


    private fun checkForWin(): Boolean {
        val field = arrayOf<ArrayList<String>>(arrayListOf(),arrayListOf(),arrayListOf())

        for (i in 0 until 3) {
            for (j in 0 until 3) {
                field[i].add(buttons[i][j].text.toString())
                Log.i(TAG, "Add field string Id is ${field[i][j]}")
            }
        }

        Log.i(TAG, "Entry check for win and start to check win ")

        for (i in 0 until 3) {
            if (field[i][0] == field[i][1] &&
                field[i][0] == field[i][2] &&
                field[i][0].isNotBlank()
            ) {
                Log.i(TAG, "Check field first line")
                return true
            }
        }

        Log.i(TAG, "Pass the first check ")

        for (i in 0 until 3) {
            if (field[0][i] == field[1][i] &&
                field[0][i] == field[2][i] &&
                field[0][i].isNotBlank()
            ){
                Log.i(TAG, "Check field second line")
                return true
            }
        }

        Log.i(TAG, "Pass the second check ")

        if (field[0][0] == field[1][1] &&
            field[0][0] == field[2][2] &&
            field[0][0].isNotBlank()
        ) {
            Log.i(TAG, "Check field cross1 line")
            return true
        }

        Log.i(TAG, "Pass the third check ")

        if (field[0][2] == field[1][1] &&
            field[0][2] == field[2][0] &&
            field[0][2].isNotBlank()
        ) {
            Log.i(TAG, "Check field cross2 line")
            return true
        }

        Log.i(TAG, "Check all done")

        return false
    }
}
