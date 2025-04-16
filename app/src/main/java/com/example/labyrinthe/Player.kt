package com.example.labyrinthe

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import kotlin.math.*

class Player(positionArg: Vector2D, inventory: List<Item>?): Entity(), UseKey {
    override var position = positionArg

    companion object {
        val paint = Paint().apply{
            color = Color.GREEN
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
    }
}