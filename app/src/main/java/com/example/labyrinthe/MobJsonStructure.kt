package com.example.labyrinthe

import kotlinx.serialization.Serializable

/**
 * Data structure that represents a serialized version of the object.
 * Used by the JsonParser.
 */
@Serializable
data class MobJsonStructure(
    val movementPattern: String,
    val weapons: List<WeaponJsonStructure>
)