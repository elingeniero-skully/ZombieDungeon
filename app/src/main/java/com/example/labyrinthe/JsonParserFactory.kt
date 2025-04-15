package com.example.labyrinthe

/**
 * The JsonParserFactory allows for dynamic selection of the appropriate parser.
 */
object JsonParserFactory {
    fun getParser(type: TileType): JsonParser {
        return when (type) {
            TileType.PLAYER -> PlayerJsonParser()
            TileType.MOB -> MobJsonParser()
            TileType.BOSS -> BossJsonParser()
            TileType.WALL -> WallJsonParser()
            TileType.DOOR -> DoorJsonParser()
        }
    }
}