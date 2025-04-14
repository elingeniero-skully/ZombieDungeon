package com.example.labyrinthe

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // When opening the app (=onCreate) the executed fun by default is showMainMenu()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showMainMenu()
    }

    // Definition of the functions that give life to the layouts
    private fun showMainMenu() {
        //Sets the content on screen to be the .xml file specified inside brackets
        setContentView(R.layout.main_menu)

        // Findng the buttons on the main menu's layout
        val startGameButton = findViewById<Button>(R.id.StartGame)
        val creditsButton = findViewById<Button>(R.id.Credits)

        // Putting the buttons on listener to execute the function below when clicked
        startGameButton.setOnClickListener {
            showGame()
        }

        creditsButton.setOnClickListener {
            showCredits()
        }
    }

    private fun showGame() {
        setContentView(R.layout.activity_main)

        // Ici tu peux initialiser ton moteur de jeu ou tes éléments
        // ex : startGameLoop(), initPlayer(), etc.
    }

    private fun showCredits() {
        setContentView(R.layout.credits)

        val backButton = findViewById<Button>(R.id.back_main_menu)
        backButton.setOnClickListener {
            showMainMenu()
        }
    }
}
