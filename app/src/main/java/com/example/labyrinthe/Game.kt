package com.example.labyrinthe

import android.content.Context

class Game(private val context: Context) : GameEventListener {
    private val levelFilePaths = mutableListOf<String>()
    private var currentLevelIndex = 0
    private lateinit var currentLevel: Level

    init {
        //Retrieve files from assets.
        levelFilePaths.add("level1.json")
        levelFilePaths.add("level2.json")
        levelFilePaths.add("level3.json")
    }

    override fun onEvent(event: GameEvent, queue: EventQueue) {
        when (event) {
            is LevelSucceedEvent -> {
                if (currentLevelIndex < (levelFilePaths.size - 1)) {
                    currentLevel.stop()
                    currentLevelIndex++
                    currentLevel = Level(context, levelFilePaths[currentLevelIndex])
                } else {
                    TODO("What happens when all the levels are succeeded so the game is finished ?")
                }

            }
            is LevelFailedEvent -> {
                currentLevel.stop()
                //TODO : What happens when the level is failed ?
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

/**
 * Events related to the Game class
 */
class LevelSucceedEvent() : GameEvent()
class LevelFailedEvent() : GameEvent()