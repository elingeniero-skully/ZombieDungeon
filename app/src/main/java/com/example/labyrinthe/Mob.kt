package com.example.labyrinthe

import android.graphics.Color
import android.graphics.Paint

/**
 * @param movementPattern Movement algorithm the Mob will use (default is RandomMovementPattern).
 */
open class Mob(positionArg: Vector2D, val movementPattern: MovementPattern = RandomMovementPattern(), inventoryArg: MutableList<Item>): Entity() {
    override var position = positionArg
    override var inventory = inventoryArg

    companion object{
        val paint = Paint().apply{
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
    }

    /**
     * Method that update the mob status at each call to tick()
     */
    fun update(map: Map) {
        movementPattern.behave(this, map)
    }
}