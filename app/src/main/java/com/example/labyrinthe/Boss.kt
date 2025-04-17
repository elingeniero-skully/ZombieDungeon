package com.example.labyrinthe

import android.graphics.Color
import android.graphics.Paint

class Boss(position: Vector2D, movementPattern: MovementPattern = RandomMovementPattern(), inventory: MutableList<Item>):
    Mob(position, movementPattern, inventory) {
    companion object {
        val paint = Paint().apply{
            color = Color.YELLOW
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
    }
}

