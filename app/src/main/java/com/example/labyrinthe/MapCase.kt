package com.example.labyrinthe

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Defines the tile on the map (used for (un)serialization of the map).
 */
@Serializable
data class MapCase(
    val x: Int,
    val y: Int,
    val type: TileType,
    val details: JsonElement
)
