package com.example.labyrinthe

interface GameEventObserver {
    fun onGameEvent(event: GameEvent)
}