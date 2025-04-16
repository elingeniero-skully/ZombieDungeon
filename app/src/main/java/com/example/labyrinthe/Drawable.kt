package com.example.labyrinthe

import android.graphics.Canvas
import android.graphics.Paint

/**
 * Certain GameObjects will be shown on screen. They must implement the Drawable interface.
 */
interface Drawable {
    var position: Vector2D //Position of the drawable object.

    /**
     * Draw method of the class on the map.
     */
    //fun draw(canvas: Canvas, tileSize: Int) Arguments may vary...
}