package com.example.labyrinthe

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * JsonParser of the Mob class.
 */
class MobJsonParser() : JsonParser() {
    override fun parse(mapCase: MapCase): Mob {
        val structure = Json.decodeFromJsonElement<MobJsonStructure>(mapCase.details)
        var movementPattern: MovementPattern = RandomMovementPattern()
        val inventory = mutableListOf<Item>()

        when (structure.movementPattern) {
            "random"   -> movementPattern = RandomMovementPattern()
            "follow"   -> movementPattern = FollowPlayerPattern()
        }

        //Adding weapons to the inventory
        for (weapon in structure.weapons) {
            when (weapon.type) {
                WeaponType.GUN -> inventory.add(Gun(weapon.name, weapon.damage))
                WeaponType.KNIFE -> inventory.add(Knife(weapon.name, weapon.damage))
            }
        }

        return Mob(Vector2D(mapCase.x, mapCase.y), movementPattern, inventory)
    }
}