package com.example.labyrinthe

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), GameEventObserver {

    lateinit var game: Game

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

        game = Game(this, container)

        //Making the activity sensitive to Game events
        EventManager.subscribe(this)

        game.start()

        refreshInventory()

        //GameView is created and managed in the Game object.

        // Linking buttons
        findViewById<Button>(R.id.btnUp).setOnClickListener {
            EventManager.notify(GameEvent.PlayerMoveRequest("up"))
        }
        findViewById<Button>(R.id.btnDown).setOnClickListener {
            EventManager.notify(GameEvent.PlayerMoveRequest("down"))
        }
        findViewById<Button>(R.id.btnLeft).setOnClickListener {
            EventManager.notify(GameEvent.PlayerMoveRequest("rotate left"))
        }
        findViewById<Button>(R.id.btnRight).setOnClickListener {
            EventManager.notify(GameEvent.PlayerMoveRequest("rotate right"))
        }

        findViewById<Button>(R.id.btnShoot).setOnClickListener{
            EventManager.notify(GameEvent.ShootEvent)
        }

        findViewById<Button>(R.id.btnSwitchActiveItem).setOnClickListener{
            game.map.player.switchItem()
            refreshInventory()
        }
    }

    private fun showCredits() {
        setContentView(R.layout.credits)

        val backButton = findViewById<Button>(R.id.back_main_menu)
        backButton.setOnClickListener {
            showMainMenu()
        }
    }

    override fun onGameEvent(event: GameEvent) {
        if (event is GameEvent.GameFinished) {
            showCredits()
        } else if (event is GameEvent.UpdateHealthEvent) {
            findViewById<TextView>(R.id.healthValueTv).setText(game.map.player.health.toString())
        }
    }

    fun refreshInventory() {
        val activeItem: Item = game.map.player.inventory[game.map.player.activeItem]
        if (activeItem is Gun) {
            findViewById<TextView>(R.id.currentWeaponTypeTv).setText("Gun")
            findViewById<TextView>(R.id.currentWeaponNameTv).setText(activeItem.name)
            findViewById<TextView>(R.id.currentWeaponDamageTv).setText((activeItem.damage).toString())
        } else if (activeItem is Knife) {
            findViewById<TextView>(R.id.currentWeaponTypeTv).setText("Knife")
            findViewById<TextView>(R.id.currentWeaponNameTv).setText(activeItem.name)
            findViewById<TextView>(R.id.currentWeaponDamageTv).setText((activeItem.damage).toString())
        }
    }
}
