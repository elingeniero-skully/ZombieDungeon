package com.example.labyrinthe

/**
 * @param movementPattern Movement algorithm the Mob will use (default is RandomMovementPattern).
 */
open class Mob(positionArg: Vector2D, movementPattern: MovementPattern = RandomMovementPattern()): Entity() {
    override var position = positionArg

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

        }
    }

    /**
     * Defines the line movement pattern
     */
    class LineMovementPattern : MovementPattern {
        override fun move() {

        }
    }
}