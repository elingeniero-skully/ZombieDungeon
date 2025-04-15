package com.example.labyrinthe

/**
 * The different Game Events of the game are declared in this file.
 */
class PlayerMovedEvent(val dx: Int, val dy: Int) : GameEvent()


/**
 * Events related to the Game class
 */
class LevelSucceedEvent() : GameEvent()
class LevelFailedEvent() : GameEvent()