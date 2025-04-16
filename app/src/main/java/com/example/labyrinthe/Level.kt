package com.example.labyrinthe

import android.content.Context

class Level(private val context: Context, val mapFilePath: String) {
    private var isRunning = false
    val map = Map(context, mapFilePath)

    init {
        EventManager.subscribe(map)
    }

    /**
     * Starts the current level loop.
     */
    fun run() {
        while(isRunning) {
            TODO("Implement : Update what's shown on the screen")
        }
    }

    /**
     * Stops the current level loop.
     */
    fun stop(){
        isRunning = false
    }
}