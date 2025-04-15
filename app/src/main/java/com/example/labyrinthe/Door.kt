package com.example.labyrinthe

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Door(position: Vector2D): Wall(position) {

}

/**
 * Parser for the Wall class. No structure (for additional info) needed for now.
 */
class DoorJsonParser() : JsonParser() {
    override fun parse(mapCase: MapCase): Door {
        return Door(Vector2D(mapCase.x, mapCase.y))
    }
}