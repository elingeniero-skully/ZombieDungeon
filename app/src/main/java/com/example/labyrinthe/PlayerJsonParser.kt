package com.example.labyrinthe

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * JsonParser of the Player class.
 */
class PlayerJsonParser() : JsonParser() {
    override fun parse(mapCase: MapCase): Player {
        val structure = Json.decodeFromJsonElement<PlayerJsonStructure>(mapCase.details)
        val inventory = mutableListOf<Item>()

        //Adding weapons to the inventory
        for (weapon in structure.weapons) {
            when (weapon.type) {
                WeaponType.GUN -> inventory.add(Gun(weapon.name, weapon.damage))
                WeaponType.KNIFE -> inventory.add(Knife(weapon.name, weapon.damage))
            }
        }

        return Player(Vector2D(mapCase.x, mapCase.y), inventory)
    }
}
