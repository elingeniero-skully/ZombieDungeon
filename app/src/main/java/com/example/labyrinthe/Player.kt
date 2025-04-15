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