package com.example.labyrinthe

import android.content.Context
import android.graphics.*
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.*
class GameView(context: Context, private val game: Game) : SurfaceView(context), SurfaceHolder.Callback {
    private val gridDimensions: Vector2D = game.currentLevel.map.maxSize
    private val player: Player = game.currentLevel.map.getPlayerObject()
    private var tileSize = 0

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        tileSize = min(width / gridDimensions.x, height / gridDimensions.y)
        render()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        tileSize = min(width / gridDimensions.x, height / gridDimensions.y)
        render()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    fun render() {
        val canvas = holder.lockCanvas()
        canvas?.let {
            it.drawColor(Color.BLACK)

            //Drawing the tiles (floor and walls)
            for (y in 0 until gridDimensions.y) {
                for (x in 0 until gridDimensions.x) {
                    val left = x * tileSize
                    val top = y * tileSize
                    val right = left + tileSize
                    val bottom = top + tileSize

                    val objectAtPosition = game.currentLevel.map.getElementByPosition(Vector2D(x,y)) //GameObject at current position

                    //Paint the tile itself
                    it.drawRect(
                        left.toFloat(),
                        top.toFloat(),
                        right.toFloat(),
                        bottom.toFloat(),
                        Paint().apply {
                            color = Color.LTGRAY
                            style = Paint.Style.STROKE
                            strokeWidth = 2f
                        }
                    )

                    //Paint the object on the tile, if any
                    when (objectAtPosition) {
                        is Door   -> it.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), Door.paint)
                        is Wall   -> it.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), Wall.paint)
                        is Boss   -> objectAtPosition.draw(it, Boss.paint, tileSize)
                        is Mob    -> objectAtPosition.draw(it, Mob.paint, tileSize)
                        is Player -> objectAtPosition.draw(it, Player.paint, tileSize)
                    }
                }
            }

            holder.unlockCanvasAndPost(it)
        }
    }

}