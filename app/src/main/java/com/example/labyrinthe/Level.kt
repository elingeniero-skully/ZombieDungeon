package com.example.labyrinthe

import android.content.Context

class Level(private val context: Context) {
    private var isRunning = false
    private lateinit var map: Map
    private val listeners = mutableListOf<GameEventListener>() //Objects that implement GameEventListener.
    val queue = EventQueue()

    /**
     * Starts the current level's loop. There is no need for real time as it is a simple 2D single-player game.
     */
    fun run() {
        while(isRunning) {
            //1) Handle events that occurred
            queue.dispatchAll(listeners)
            TODO("Implement : Update what's shown on the screen")
        }
    }

    /**
     * Stops the current level's loop.
     */
    fun stop(){
        isRunning = false
    }

    /**
     * Loads the map that resides in filepath
     */
    fun loadMap(filepath: String) {
        map = Map(context, filepath)
    }
}