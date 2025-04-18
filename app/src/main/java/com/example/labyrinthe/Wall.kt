package com.example.labyrinthe

import android.graphics.Color
import android.graphics.Paint

open class Wall(override var position: Vector2D): GameObject(), Drawable {
    companion object {
        var paint = Paint().apply{
            color = Color.GRAY
            style = Paint.Style.FILL
        }
    }
}