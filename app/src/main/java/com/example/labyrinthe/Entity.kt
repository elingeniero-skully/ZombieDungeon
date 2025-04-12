package com.example.labyrinthe

abstract class Entity: GameObject(), Drawable {
    var health = 100
    var inventory = mutableListOf<Item>()

}