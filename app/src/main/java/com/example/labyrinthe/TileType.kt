package com.example.labyrinthe

import kotlinx.serialization.Serializable

/**
 * Defines different types of tiles on the map (used for (un)serialization of the map).
 * Used by the JsonParserFactory object
 * Has the benefit of forcing the JsonParserFactory to implement each case.
 */
@Serializable
enum class TileType { WALL, DOOR, PLAYER, MOB, BOSS }