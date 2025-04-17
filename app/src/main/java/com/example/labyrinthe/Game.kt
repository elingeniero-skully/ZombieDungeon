package com.example.labyrinthe

import android.content.Context
import android.widget.FrameLayout

class Game(private val context: Context, private val container: FrameLayout) : GameEventObserver {
    private val levelFilePaths = mutableListOf<String>()
    private var currentLevelIndex = 0
    lateinit var gameView: GameView
    lateinit var map: Map
    lateinit var gameLoop: GameLoop

    init {
        //Retrieve files from assets.
        levelFilePaths.add("level1.json")
        levelFilePaths.add("level2.json")
        EventManager.subscribe(this)
    }

    /**
     * Main game loop.
     */
    fun tick() {
        //Update the mobs
        for (mob in map.mobs) {
            mob.update(map)
        }

        gameView.render()
    }

    override fun onGameEvent(event: GameEvent) {
        when (event) {
            is GameEvent.LevelSucceedEvent -> {
                if (currentLevelIndex < (levelFilePaths.size - 1)) {
                    gameLoop.stop()
                    unloadMap()
                    currentLevelIndex++
                    loadMap(levelFilePaths[currentLevelIndex])
                    updateView()
                    gameLoop.start()
                } else {
                    //When the game is finished (success in this case)
                    gameLoop.stop()
                    unloadMap()
                    currentLevelIndex = 0
                    EventManager.notify(GameEvent.GameFinished)
                }

            }
            is GameEvent.LevelFailedEvent -> {
                gameLoop.stop()
                unloadMap()
                EventManager.unsubscribe(gameView)
                currentLevelIndex = 0
                EventManager.notify(GameEvent.GameFinished)
            }
            else -> {}
        }
    }

    /**
     * Updates the view
     */
    fun updateView() {
        gameView = GameView(context, this)
        EventManager.subscribe(gameView) //Make gameView reactive to the game events.
        container.removeAllViews()
        container.addView(gameView)
    }

    /**
     * Loads a new map and subscribes it to the EventManager.
     */
    fun loadMap(filePath: String) {
        map = Map(context, filePath)
        EventManager.subscribe(map)
    }

    /**
     * Unloads the map by unsubscribing to the EventManager.
     */
    fun unloadMap() {
        EventManager.unsubscribe(map)
    }

    fun start() {
        //Start the first level.
        loadMap(levelFilePaths[0])

        gameView = GameView(context, this)
        EventManager.subscribe(gameView) //Make gameView reactive to the game events.
        container.addView(gameView)

        //Start the game main loop.
        gameLoop = GameLoop(this)
        gameLoop.start()
    }

    fun stop() {
        gameLoop.stop()
    }
}