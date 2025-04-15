package com.example.labyrinthe

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Player(positionArg: Vector2D, inventory: List<Item>?): Entity(), UseKey, GameEventListener {
    override var position = positionArg

    override fun onEvent(event: GameEvent, queue: EventQueue) {
        when (event) {
            is PlayerMovedEvent -> {
                //Request a move to the map.
            }
        }
    }
}

/**
 * Data structure that represents a serialized version of the object.
 * Used by the JsonParser.
 */
@Serializable
class PlayerStructure(
    val weapons: List<WeaponStructure>
)

/**
 * Parser of the class.
 */
class PlayerJsonParser() : JsonParser() {
    override fun parse(mapCase: MapCase): Player {
        val structure = Json.decodeFromJsonElement<PlayerStructure>(mapCase.details)
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

/**
 * Events related to the Player class
 */
class PlayerMovedEvent(val dx: Int, val dy: Int) : GameEvent()