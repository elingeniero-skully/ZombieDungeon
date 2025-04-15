package com.example.labyrinthe

import kotlinx.serialization.Serializable

/**
 * Data structure that represents a serialized version of the object.
 * Used by the JsonParser.
 */
@Serializable
data class PlayerJsonStructure(
    val weapons: List<WeaponJsonStructure>
)
