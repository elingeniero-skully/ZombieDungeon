package com.example.labyrinthe

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Boss(position: Vector2D, movementPattern: MovementPattern = RandomMovementPattern()): Mob(position, movementPattern) {


}

/**
 * Data structure that represents a serialized version of the object.
 * Used by the JsonParser.
 */
@Serializable
data class BossStructure(
    val x: Int,
    val y: Int,
    val movementPattern: String
)

/**
 * Parser of the class.
 */
class BossJsonParser() : JsonParser() {
    override fun parse(mapCase: MapCase): Boss {
        val structure = Json.decodeFromJsonElement<BossStructure>(mapCase.details)
        var movementPattern: Mob.MovementPattern = Mob.RandomMovementPattern()

        when (structure.movementPattern) {
            "random"   -> movementPattern = Mob.RandomMovementPattern()
            "follow"   -> movementPattern = Mob.FollowPlayerPattern()
            "line"     -> movementPattern = Mob.LineMovementPattern()
            "circular" -> movementPattern = Mob.CircularMovementPattern()
        }

        return Boss(Vector2D(structure.x, structure.y), movementPattern)
    }
}