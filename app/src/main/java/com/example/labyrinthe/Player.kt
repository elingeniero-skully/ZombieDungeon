package com.example.labyrinthe

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Player(val positionArg: Vector2D): Entity(), UseKey, GameEventListener {
    override var position = positionArg

    override fun onEvent(event: GameEvent, queue: EventQueue) {
        when (event) {
            is PlayerMovedEvent -> {
                //Request a move to the map.
            }
        }
    }
}

/**
 * Data structure that represents a serialized version of the object.
 * Used by the JsonParser.
 */
@Serializable
data class PlayerStructure(
    val x: Int,
    val y: Int
)

/**
 * Parser of the class.
 */
class PlayerJsonParser() : JsonParser() {
    override fun parse(mapCase: MapCase): Player {
        val structure = Json.decodeFromJsonElement<PlayerStructure>(mapCase.details)
        return Player(Vector2D(structure.x, structure.y))
    }
}

/**
 * Events related to the Player class
 */
class PlayerMovedEvent(val dx: Int, val dy: Int) : GameEvent()