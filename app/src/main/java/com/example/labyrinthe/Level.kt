package com.example.labyrinthe

import android.content.Context

class Level(private val context: Context, val mapFilePath: String) {
    private var isRunning = false
    val map = Map(context, mapFilePath)

    init {
        EventManager.subscribe(map)
    }

    /**
     * Stops the current level loop.
     */
    fun stop(){
        EventManager.unsubscribe(map)
    }
}