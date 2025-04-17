package com.example.labyrinthe

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * Parser of the Boss class. Mob structure is suitable to store the Json data of the Boss class.
 */
class BossJsonParser() : JsonParser() {
    override fun parse(mapCase: MapCase): Boss {
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

        return Boss(Vector2D(mapCase.x, mapCase.y), movementPattern, inventory)
    }
}