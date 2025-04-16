package com.example.labyrinthe

import android.graphics.Color
import android.graphics.Paint

open class Wall(positionArg: Vector2D): GameObject(), Drawable {
    override var position = positionArg

    companion object {
        var paint = Paint().apply{
            color = Color.GRAY
            style = Paint.Style.FILL
        }
    }
}