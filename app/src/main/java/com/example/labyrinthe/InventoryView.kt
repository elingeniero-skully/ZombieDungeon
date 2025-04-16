package com.example.labyrinthe

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.*
class InventoryView(context: Context, private val game: Game) : SurfaceView(context), SurfaceHolder.Callback, GameEventObserver {

    private lateinit var gridDimensions: Vector2D
    private val player: Player = game.map.findObjectOfType<Player>()
    private var inventoryTileSize = 0
    private var activeItemId = 0
    private var itemsInTheInventory = mutableListOf<Item>()

    // Declare Paint objects upfront for performance optimization
    private val defaultPaint = Paint().apply {
        color = Color.CYAN
        style = Paint.Style.FILL
        strokeWidth = 2f
    }
    private val gunPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
        strokeWidth = 2f
    }
    private val knifePaint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
        strokeWidth = 2f
    }

    init {
        holder.addCallback(this)

        // Retrieve the inventory of the player
        itemsInTheInventory = player.inventory

        // Show all the objects in the inventory
        gridDimensions = Vector2D(itemsInTheInventory.size, 1)

        // By default, first item is active
        activeItemId = 0
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        inventoryTileSize = min(width / gridDimensions.x, height / gridDimensions.y)
        render()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        inventoryTileSize = min(width / gridDimensions.x, height / gridDimensions.y)
        render()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    override fun onGameEvent(event: GameEvent) {
        if (event is GameEvent.RenderEvent) {
            render()
        }

        if (event is GameEvent.SwitchActiveItemEvent) {
            itemsInTheInventory = player.inventory
            activeItemId = (activeItemId + 1) % itemsInTheInventory.size
            render()
        }
    }

    /**
     * Rendering method: updates content on the screen.
     */
    fun render() {
        val canvas = holder.lockCanvas()
        canvas?.let {
            it.drawColor(Color.WHITE)

            // Retrieve the inventory of the player
            itemsInTheInventory = player.inventory

            // Show all the objects in the inventory
            gridDimensions = Vector2D(itemsInTheInventory.size, 1)

            // Drawing the tiles
            for (x in 0 until itemsInTheInventory.size) {
                val left = x * inventoryTileSize + this.left  // Add the view's left offset
                val top = 0
                val right = left + inventoryTileSize
                val bottom = inventoryTileSize

                Log.d("InventoryView", "Drawing tile at ($left, $top), ($right, $bottom)")
                Log.d("InventoryView", "InventoryView dimensions: width = $width, height = $height")

                // Paint the tile itself
                it.drawRect(
                    left.toFloat(),
                    top.toFloat(),
                    right.toFloat(),
                    bottom.toFloat(),
                    Wall.paint
                )

                // Paint the object on the tile
                when (val item = itemsInTheInventory[x]) {
                    is Gun -> it.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), Door.paint)
                    is Knife -> it.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), Wall.paint)
                }

                // Add something to the active item.
                if (x == activeItemId) {
                    it.drawRect(
                        left.toFloat(),
                        top.toFloat(),
                        right.toFloat(),
                        bottom.toFloat(),
                        Paint().apply{
                            color = Color.RED
                            style = Paint.Style.STROKE
                            strokeWidth = 20f
                        }
                    )
                }
            }

            holder.unlockCanvasAndPost(it)
        }
    }
}
