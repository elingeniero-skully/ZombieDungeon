package com.example.labyrinthe

import android.graphics.Color
import android.graphics.Paint
/**
 * @param movementPattern Movement algorithm the Mob will use (default is RandomMovementPattern).
 */

//TODO Include item in the constructor AND IN THE MOB STRUCTURE PROPERLY !!
open class Mob(positionArg: Vector2D, movementPattern: MovementPattern = RandomMovementPattern(), inventory: List<Item>?): Entity() {
    override var position = positionArg

    override var paint = Paint().apply{
        color = Color.LTGRAY
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    /**
     * IMPLEMENTATION OF THE STRATEGY PATTERN
     * The Movement Patterns define the Mob's logic : how it moves and when it attacks.
     */
    interface MovementPattern {
        fun move() //Moves and attacks.
    }

    /**
     * Defines the random movement pattern
     */
    class RandomMovementPattern : MovementPattern {
        override fun move() {
            TODO("Not yet implemented")
        }
    }

    /**
     * Follows the Player by using an observer.
     * With an observer.
     */
    class FollowPlayerPattern : MovementPattern {
        override fun move() {
            TODO("Not yet implemented")
        }
    }

    /**
     * Defines the circular movement pattern
     */
    class CircularMovementPattern : MovementPattern {
        override fun move() {
            TODO("Not yet implemented")
        }
    }

    /**
     * Defines the line movement pattern
     */
    class LineMovementPattern : MovementPattern {
        override fun move() {
            TODO("Not yet implemented")
        }
    }
}