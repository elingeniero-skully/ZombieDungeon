package com.example.labyrinthe

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * @param movementPattern Movement algorithm the Mob will use (default is RandomMovementPattern).
 */

//TODO Include item in the constructor AND IN THE MOB STRUCTURE PROPERLY !!
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

/**
 * Data structure that represents a serialized version of the object.
 * Used by the JsonParser.
 */
@Serializable
data class MobStructure(
    val x: Int,
    val y: Int,
    val movementPattern: String
)

/**
 * Parser of the class.
 */
class MobJsonParser() : JsonParser() {
    override fun parse(mapCase: MapCase): Mob {
        val structure = Json.decodeFromJsonElement<MobStructure>(mapCase.details)
        var movementPattern: Mob.MovementPattern = Mob.RandomMovementPattern()

        when (structure.movementPattern) {
            "random"   -> movementPattern = Mob.RandomMovementPattern()
            "follow"   -> movementPattern = Mob.FollowPlayerPattern()
            "line"     -> movementPattern = Mob.LineMovementPattern()
            "circular" -> movementPattern = Mob.CircularMovementPattern()
        }

        return Mob(Vector2D(structure.x, structure.y), movementPattern)
    }
}