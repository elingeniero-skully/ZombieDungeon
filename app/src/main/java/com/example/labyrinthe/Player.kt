package com.example.labyrinthe

import android.graphics.Color
import android.graphics.Paint

class Player(positionArg: Vector2D, inventory: List<Item>?): Entity(), UseKey, GameEventListener {
    override var position = positionArg

    override var paint = Paint().apply{
        color = Color.LTGRAY
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    override fun onEvent(event: GameEvent, queue: EventQueue) {
        when (event) {
            is PlayerMovedEvent -> {
                //Request a move to the map.
            }
        }
    }
}