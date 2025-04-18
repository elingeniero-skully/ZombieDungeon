package com.example.labyrinthe

import kotlinx.coroutines.*

/**
 * The game loop is necessary in order to interact with the mobs.
 */
class GameLoop(val map: Map, val gameView: GameView) {
    private var job: Job? = null
    fun start() {
        job = CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                withContext(Dispatchers.Main) {
                    tick() // Update game logic
                }

                if(gameDifficultyMax) {
                    delay(650L)
                } else {
                    delay(1500L) // how many iterations per second ?
                }
            }
        }
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

    fun stop() {
        job?.cancel()
    }
}
