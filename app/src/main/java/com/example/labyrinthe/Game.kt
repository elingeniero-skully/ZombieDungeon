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
        levelFilePaths.add("level3.json")
        EventManager.subscribe(this)
    }

    override fun onGameEvent(event: GameEvent) {
        when (event) {
            is GameEvent.LevelSucceedEvent -> {
                //Is there a level after ?
                if (currentLevelIndex < (levelFilePaths.size - 1)) {
                    //Stop the game loop
                    gameLoop.stop()

                    //"Unload" the map
                    EventManager.unsubscribe(map)

                    //"Unload" the old gameView
                    EventManager.unsubscribe(gameView)

                    //Remove the old gameView
                    container.removeAllViews()

                    currentLevelIndex++

                    //Load the next map
                    map = Map(context, levelFilePaths[currentLevelIndex])

                    //Begin a new gameView
                    gameView = GameView(context, map)
                    container.addView(gameView)

                    //Initialize a new game loop
                    gameLoop = GameLoop(map, gameView)

                    //Make the map and the gameView reactive to game events.
                    EventManager.subscribe(gameView)
                    EventManager.subscribe(map)

                    EventManager.notify(GameEvent.UpdateHealthEvent(100))

                    //Maybe a NEW gameLoop
                    gameLoop.start()

                //When the game is finished (success in this case)
                } else {
                    //Stop the game loop
                    gameLoop.stop()

                    //Unload the map
                    EventManager.unsubscribe(map)

                    //Unload the old gameView
                    EventManager.unsubscribe(gameView)

                    //Remove the old gameView
                    container.removeAllViews()

                    EventManager.notify(GameEvent.GameFinished)
                }

            }
            is GameEvent.LevelFailedEvent -> {
                //Stop the game loop
                gameLoop.stop()

                //Unload the map
                EventManager.unsubscribe(map)

                //Unload the old gameView
                EventManager.unsubscribe(gameView)

                //Remove the old gameView
                container.removeAllViews()
            }
            else -> {}
        }
    }

    fun start() {

        currentLevelIndex = 0

        //Load the first map
        map = Map(context, levelFilePaths[0])

        //Begin a new gameView
        gameView = GameView(context, map)
        container.addView(gameView)

        //Initialize a new game loop
        gameLoop = GameLoop(map, gameView)

        //Make the map and the gameView reactive to game events.
        EventManager.subscribe(gameView)
        EventManager.subscribe(map)

        EventManager.notify(GameEvent.UpdateHealthEvent(100))

        //Maybe a NEW gameLoop
        gameLoop.start()
    }
}