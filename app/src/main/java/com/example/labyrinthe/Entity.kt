package com.example.labyrinthe

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import kotlin.math.*

abstract class Entity(override var position: Vector2D, val inventory: MutableList<Item>): GameObject(), Drawable {

    var health = 100
    var sightDirection = 0
    var activeItem: Int = 0

    /**
     * Selects the next item in the inventory
     */
    fun switchItem() {
        activeItem = (activeItem + 1) % inventory.size
    }

    /**
     * Movement methods.
     */

    fun moveForward() {
        //This method should be called AFTER the collision check.
        position += Vector2D.fromSightDirection(sightDirection)
    }

    fun moveBackward() {
        //This method should be called AFTER the collision check.
        position -= Vector2D.fromSightDirection(sightDirection)
    }

    fun rotateRight() {
        sightDirection = (sightDirection + 1) % 4
    }

    fun rotateLeft() {
        sightDirection = (sightDirection + 3) % 4
    }

    /**
     * Defines how to draw an entity on the map. Used by GameView.
     * The method currently draws a triangle. The color of this triangle will vary depending on the entity.
     */
    fun draw(canvas: Canvas, paint: Paint, tileSize: Int) {
        val centerX = (position.x * tileSize + tileSize / 2).toFloat()
        val centerY = (position.y * tileSize + tileSize / 2).toFloat()

        val length = tileSize * 0.45f // pointe
        val base = tileSize * 0.3f    // largeur arrière

        val angleRad = when (sightDirection) {
            0 -> 0.0
            1 -> PI / 2
            2 -> PI
            3 -> -PI / 2
            else -> 0.0
        }

        val tipX = centerX + cos(angleRad).toFloat() * length
        val tipY = centerY + sin(angleRad).toFloat() * length

        val leftBaseX = centerX + cos(angleRad + 5 * PI / 6).toFloat() * base
        val leftBaseY = centerY + sin(angleRad + 5 * PI / 6).toFloat() * base

        val rightBaseX = centerX + cos(angleRad - 5 * PI / 6).toFloat() * base
        val rightBaseY = centerY + sin(angleRad - 5 * PI / 6).toFloat() * base

        val path = Path().apply {
            moveTo(tipX, tipY)
            lineTo(leftBaseX, leftBaseY)
            lineTo(rightBaseX, rightBaseY)
            close()
        }

        canvas.drawPath(path, paint)
    }
}