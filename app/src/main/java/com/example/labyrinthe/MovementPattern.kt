package com.example.labyrinthe

/**
 * IMPLEMENTATION OF THE STRATEGY PATTERN
 * The Movement Patterns define the Mob's logic : how it moves and when it attacks.
 */
interface MovementPattern {
    fun behave(mob: Mob, map: Map) //Moves and attacks.
}