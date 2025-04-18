package com.example.labyrinthe

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

//The only global variable in the game !
var gameDifficultyMax = false

class MainActivity : AppCompatActivity(), GameEventObserver {

    // When opening the app (=onCreate) the executed fun by default is showMainMenu()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showMainMenu()
    }

    // Definition of the functions that give life to the layouts
    private fun showMainMenu() {
        //Sets the content on screen to be the .xml file specified inside brackets
        setContentView(R.layout.main_menu)

        // Finding the buttons on the main menu's layout
        val startGameButton = findViewById<Button>(R.id.StartGame)
        val creditsButton = findViewById<Button>(R.id.Credits)

        val gameDifficulty = findViewById<SwitchCompat>(R.id.switch2)

        // Putting the buttons on listener to execute the function below when clicked
        startGameButton.setOnClickListener {
            gameDifficultyMax = gameDifficulty.isChecked
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

        val game = Game(this, container)

        //Making the activity sensitive to Game events
        EventManager.subscribe(this)

        game.start()

        refreshInventory(game.map.player.inventory[game.map.player.activeItem])

        //GameView is created and managed in the Game object.

        // Linking buttons
        findViewById<Button>(R.id.btnUp).setOnClickListener {
            game.map.move(game.map.player, "up")
            EventManager.notify(GameEvent.RenderEvent)
        }
        findViewById<Button>(R.id.btnDown).setOnClickListener {
            game.map.move(game.map.player, "down")
            EventManager.notify(GameEvent.RenderEvent)
        }
        findViewById<Button>(R.id.btnLeft).setOnClickListener {
            game.map.move(game.map.player, "rotate left")
            EventManager.notify(GameEvent.RenderEvent)
        }
        findViewById<Button>(R.id.btnRight).setOnClickListener {
            game.map.move(game.map.player, "rotate right")
            EventManager.notify(GameEvent.RenderEvent)
        }

        findViewById<Button>(R.id.btnShoot).setOnClickListener{
            game.map.playerAttack()
        }

        findViewById<Button>(R.id.btnSwitchActiveItem).setOnClickListener{
            game.map.player.switchItem()
            refreshInventory(game.map.player.inventory[game.map.player.activeItem])
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
            setContentView(R.layout.game_finished)

            val assetFileDescriptor = this.resources.openRawResourceFd(R.raw.music)
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.length
            )
            assetFileDescriptor.close()
            mediaPlayer.isLooping = true
            mediaPlayer.prepare()
            mediaPlayer.start()

            val quitButton = findViewById<Button>(R.id.quitButton)
            quitButton.setOnClickListener {
                this.finishAffinity() //Leave the app
            }

        } else if (event is GameEvent.UpdateHealthEvent) {
            findViewById<TextView>(R.id.healthValueTv).text = event.health.toString()
        } else if (event is GameEvent.LevelFailedEvent) {
            setContentView(R.layout.game_failed)

            val quitButton = findViewById<Button>(R.id.quitButtonFail)
            quitButton.setOnClickListener {
                this.finishAffinity() //Leave the app
            }
        }
    }

    fun refreshInventory(activeItem: Item) {
        if (activeItem is Gun) {
            findViewById<TextView>(R.id.currentWeaponTypeTv).text = "Gun"
            findViewById<TextView>(R.id.currentWeaponNameTv).text = activeItem.name
            findViewById<TextView>(R.id.currentWeaponDamageTv).text = (activeItem.damage).toString()
        } else if (activeItem is Knife) {
            findViewById<TextView>(R.id.currentWeaponTypeTv).text = "Knife"
            findViewById<TextView>(R.id.currentWeaponNameTv).text = activeItem.name
            findViewById<TextView>(R.id.currentWeaponDamageTv).text = (activeItem.damage).toString()
        }
    }
}
