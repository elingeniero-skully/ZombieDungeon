package com.example.labyrinthe

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Boss(position: Vector2D, movementPattern: MovementPattern = RandomMovementPattern(), inventory: List<Item>?):
    Mob(position, movementPattern, inventory) {


}

