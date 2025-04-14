package com.example.labyrinthe

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
open class Wall(positionArg: Vector2D): GameObject(), Drawable {
    override var position = positionArg
}

/**
 * Data structure that represents a serialized version of a Wall object.
 * Used by the JsonParser.
 */
@Serializable
data class WallStructure(
    val x: Int,
    val y: Int
)

/**
 * Parser for the Wall class.
 */
class WallJsonParser() : JsonParser() {
    override fun parse(mapCase: MapCase): Wall {
        val structure = Json.decodeFromJsonElement<WallStructure>(mapCase.details)
        return Wall(Vector2D(structure.x, structure.y))
    }
}