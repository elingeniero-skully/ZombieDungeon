package com.example.labyrinthe

import android.content.Context

class Level(private val context: Context) {
    private var isRunning = false
    private lateinit var map: Map
    private val listeners = mutableListOf<GameEventListener>() //Objects that implement GameEventListener.

    /**
     * Starts the current level's loop. There is no need for real time as it is a simple 2D single-player game.
     */
    fun run() {
        while(isRunning) {
            dispatchEvents() //Handle events that occurred
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

    /**
     * Dispatches the events in the EventQueue to the relevant listeners.
     */
    fun dispatchEvents() {
        val events = EventQueue.pollAll()
        for (event in events) {
            listeners.forEach { it.onEvent(event) }
        }
    }

}