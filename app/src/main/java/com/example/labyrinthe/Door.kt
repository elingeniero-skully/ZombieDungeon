package com.example.labyrinthe

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Door(position: Vector2D): Wall(position) {

}

/**
 * Data structure that represents a serialized version of a Wall object.
 * Used by the JsonParser.
 */
@Serializable
data class DoorStructure(
    val x: Int,
    val y: Int
)

/**
 * Parser for the Wall class.
 */
class DoorJsonParser() : JsonParser() {
    override fun parse(mapCase: MapCase): Door {
        val structure = Json.decodeFromJsonElement<DoorStructure>(mapCase.details)
        return Door(Vector2D(structure.x, structure.y))
    }
}