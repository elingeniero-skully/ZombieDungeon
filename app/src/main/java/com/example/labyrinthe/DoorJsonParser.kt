package com.example.labyrinthe

/**
 * Parser for the Wall class. No structure (for additional info) needed for now.
 */
class DoorJsonParser() : JsonParser() {
    override fun parse(mapCase: MapCase): Door {
        return Door(Vector2D(mapCase.x, mapCase.y))
    }
}