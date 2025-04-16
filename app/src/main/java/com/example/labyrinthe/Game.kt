package com.example.labyrinthe

import android.content.Context
import android.widget.FrameLayout

class Game(private val context: Context, private val container: FrameLayout) : GameEventObserver {
    private val levelFilePaths = mutableListOf<String>()
    private var currentLevelIndex = 0
    lateinit var currentLevel: Level
    lateinit var gameView: GameView

    init {
        //Retrieve files from assets.
        levelFilePaths.add("level1.json")
        levelFilePaths.add("level2.json")
        EventManager.subscribe(this)
    }

    override fun onGameEvent(event: GameEvent) {
        when (event) {
            is GameEvent.LevelSucceedEvent -> {
                if (currentLevelIndex < (levelFilePaths.size - 1)) {
                    currentLevel.stop()
                    currentLevelIndex++
                    currentLevel = Level(context, levelFilePaths[currentLevelIndex])
                    EventManager.notify(GameEvent.RenderEvent)
                } else {
                    EventManager.notify(GameEvent.GameFinished)
                }

            }
            is GameEvent.LevelFailedEvent -> {
                currentLevel.stop()
                EventManager.notify(GameEvent.GameFinished)
            }
            else -> {}
        }
    }

    fun start() {
        currentLevel = Level(context, levelFilePaths[0])
        gameView = GameView(context, this)
        EventManager.subscribe(gameView) //Make gameView reactive to the game events.
        container.addView(gameView)
    }
}