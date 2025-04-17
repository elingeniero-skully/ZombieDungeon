package com.example.labyrinthe

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import kotlin.math.*

class Player(positionArg: Vector2D, inventoryArg: MutableList<Item>): Entity() {
    override var position = positionArg
    override var inventory: MutableList<Item> = inventoryArg
    var activeItem: Int = 0

    companion object {
        val paint = Paint().apply{
            color = Color.GREEN
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
    }

    /**
     * Selects the next item in the inventory
     */
    fun switchItem() {
        activeItem = (activeItem + 1) % inventory.size
    }
}