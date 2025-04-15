package com.example.labyrinthe

/**
 * Parser for the Wall class. No structure (for additional info) needed for now.
 */
class WallJsonParser() : JsonParser() {
    override fun parse(mapCase: MapCase): Wall {
        return Wall(Vector2D(mapCase.x, mapCase.y))
    }
}