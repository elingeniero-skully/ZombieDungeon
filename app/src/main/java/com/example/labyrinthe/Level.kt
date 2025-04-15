package com.example.labyrinthe

import android.content.Context

class Level(private val context: Context) {
    private var isRunning = false
    private lateinit var map: Map
    private val listeners = mutableListOf<GameEventListener>() //Objects that implement GameEventListener.
    val queue = EventQueue()

    /**
     * Starts the current level loop. There is no need for real time as it is a simple 2D single-player game.
     */
    fun run() {
        while(isRunning) {
            //1) Handle events that occurred
            queue.dispatchAll(listeners)
            TODO("Implement : Update what's shown on the screen")
        }
    }

    /**
     * Stops the current level loop.
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
     * Allows external objets to register external events.
     */
    fun registerExternalEvent(event: GameEvent) {
        queue.enqueueExternal(event)
    }

    /**
     * Allows external objets to register internal events. Internal events can only be added from the Listeners.
     * More more information see the relevant sequence diagram describing in-game event handling.
     */
    fun registerInternalEvent(event: GameEvent, caller: Any) {
        if (caller is GameEventListener) queue.enqueueInternal(event)
    }
}