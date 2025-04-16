package com.example.labyrinthe

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
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
        // Here you can initialize your game engine or elements
        // e.g. startGameLoop(), initPlayer(), etc.

        val container = findViewById<FrameLayout>(R.id.gameMapContainer)
        val game = Game(this)
        game.start()
        val gameView = GameView(this, game)
        container.addView(gameView)

        // Linking buttons
        findViewById<Button>(R.id.btnUp).setOnClickListener {
            //gameView.moveForward()
        }
        findViewById<Button>(R.id.btnDown).setOnClickListener {
            //gameView.moveBackward()
        }
        findViewById<Button>(R.id.btnLeft).setOnClickListener {
            //gameView.rotateLeft()
        }
        findViewById<Button>(R.id.btnRight).setOnClickListener {
            //gameView.rotateRight()
        }

        findViewById<Button>(R.id.btnShoot).setOnClickListener{
            //TODO: implement shoot method and link it to the corresponding button
        }
        findViewById<Button>(R.id.btnInventory).setOnClickListener{
            //TODO: make the inventory appear with the items shown
        }

    }

    private fun showCredits() {
        setContentView(R.layout.credits)

        val backButton = findViewById<Button>(R.id.back_main_menu)
        backButton.setOnClickListener {
            showMainMenu()
        }
    }
}
