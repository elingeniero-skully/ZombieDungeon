package com.example.labyrinthe

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import kotlin.math.*

class Player(positionArg: Vector2D, inventory: List<Item>?): Entity(), UseKey, GameEventListener {
    override var position = positionArg

    companion object {
        val paint = Paint().apply{
            color = Color.LTGRAY
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
    }

    override fun onEvent(event: GameEvent, queue: EventQueue) {
        when (event) {
            is PlayerMovedEvent -> {
                //Request a move to the map.
            }
        }
    }
}