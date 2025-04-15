package com.example.labyrinthe

import kotlinx.serialization.Serializable

/**
 * Base structure in which a map is (un)serialized.
 */
@Serializable
data class MapData(
    val width: Int,
    val height: Int,
    val cases: List<MapCase>
)
