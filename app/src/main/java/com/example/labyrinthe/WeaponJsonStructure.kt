package com.example.labyrinthe

import kotlinx.serialization.Serializable

/**
 * Data structure that represents a serialized version of the object.
 * Used by the JsonParser.
 */
@Serializable
data class WeaponJsonStructure(
    val type: WeaponType,
    val name: String,
    val damage: Int
)
