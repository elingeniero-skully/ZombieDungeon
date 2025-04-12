package com.example.labyrinthe

open class Wall(val positionArg: Vector2D): GameObject(), Drawable {
    override var position = positionArg
}