package com.example.labyrinthe

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Boss(position: Vector2D, movementPattern: MovementPattern = RandomMovementPattern(), inventory: List<Item>?):
    Mob(position, movementPattern, inventory) {


}

/**
 * Data structure that represents a serialized version of the object.
 * Used by the JsonParser.
 */
@Serializable
data class BossStructure(
    val movementPattern: String,
    val weapons: List<WeaponStructure>
)

/**
 * Parser of the class.
 */
class BossJsonParser() : JsonParser() {
    override fun parse(mapCase: MapCase): Boss {
        val structure = Json.decodeFromJsonElement<BossStructure>(mapCase.details)
        var movementPattern: Mob.MovementPattern = Mob.RandomMovementPattern()
        val inventory = mutableListOf<Item>()

        when (structure.movementPattern) {
            "random"   -> movementPattern = Mob.RandomMovementPattern()
            "follow"   -> movementPattern = Mob.FollowPlayerPattern()
            "line"     -> movementPattern = Mob.LineMovementPattern()
            "circular" -> movementPattern = Mob.CircularMovementPattern()
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