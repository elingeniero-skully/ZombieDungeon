package com.example.labyrinthe

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
open class Wall(positionArg: Vector2D): GameObject(), Drawable {
    override var position = positionArg
}