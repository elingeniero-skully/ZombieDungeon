package com.example.labyrinthe

import kotlinx.serialization.Serializable

open class Weapon(name: String, damage: Int): Item() {
}

enum class WeaponType { GUN, KNIFE }
/**
 * Data structure that represents a serialized version of the object.
 * Used by the JsonParser.
 */
@Serializable
class WeaponJsonStructure(
    val type: WeaponType,
    val name: String,
    val damage: Int
)
