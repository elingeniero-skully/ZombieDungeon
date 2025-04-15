package com.example.labyrinthe

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
open class Wall(positionArg: Vector2D): GameObject(), Drawable {
    override var position = positionArg
}

/**
 * Parser for the Wall class. No structure (for additional info) needed for now.
 */
class WallJsonParser() : JsonParser() {
    override fun parse(mapCase: MapCase): Wall {
        return Wall(Vector2D(mapCase.x, mapCase.y))
    }
}