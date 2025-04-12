package com.example.labyrinthe

class Player(val positionArg: Vector2D): Entity(), UseKey {
    override var position = positionArg
}