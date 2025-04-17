package com.example.labyrinthe

import kotlinx.coroutines.*

/**
 * The game loop is necessary in order to interact with the mobs.
 */
class GameLoop(val game: Game) {
    private var job: Job? = null
    fun start() {
        job = CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                withContext(Dispatchers.Main) {
                    game.tick() // Update game logic
                }

                delay(1700L) // how many iterations per second ?
            }
        }
    }

    fun stop() {
        job?.cancel()
    }
}
