package com.example.labyrinthe

class Level {
    private var isRunning = false
    init {
        //Game loop ?
        //What happens when a new level is created ?
        val map = Map()
    }

    /**
     * Starts the current level's loop. There is no need for real time as it is a simple 2D single-player game.
     */
    fun run() {
        while(isRunning) {
            //1) Listen to events if any
            //2) Handle them
            //3) Update what's shown on the screen.
            TODO("Implement")
        }
    }

    /**
     * Stops the current level's loop.
     */
    fun stop(){
        isRunning = false
    }
}