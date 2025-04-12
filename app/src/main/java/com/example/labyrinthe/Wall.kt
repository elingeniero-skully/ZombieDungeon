package com.example.labyrinthe

open class Wall(positionArg: Vector2D): GameObject(), Drawable {
    override var position = positionArg
}