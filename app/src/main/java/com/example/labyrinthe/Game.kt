package com.example.labyrinthe

import android.content.Context

class Game(private val context: Context) : GameEventObserver {
    private val levelFilePaths = mutableListOf<String>()
    private var currentLevelIndex = 0
    lateinit var currentLevel: Level

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
                if (currentLevelIndex < (levelFilePaths.size - 1)) {
                    currentLevel.stop()
                    currentLevelIndex++
                    currentLevel = Level(context, levelFilePaths[currentLevelIndex])
                } else {
                    TODO("What happens when all the levels are succeeded so the game is finished ?")
                }

            }
            is GameEvent.LevelFailedEvent -> {
                currentLevel.stop()
                //TODO : What happens when the level is failed ?
            }
            else -> {

            }
        }
    }

    /**
     * Starts the game at level 0
     */
    fun start() {
        currentLevel = Level(context, levelFilePaths[0])
    }
}