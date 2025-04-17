package com.example.labyrinthe

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import kotlin.math.*

class Player(position: Vector2D, inventory: MutableList<Item>): Entity(position, inventory) {
    companion object {
        val paint = Paint().apply{
            color = Color.GREEN
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
    }
}