package com.example.labyrinthe

import android.content.Context
import android.widget.FrameLayout

class Game(private val context: Context, private val container: FrameLayout, private val inventoryContainer: FrameLayout) : GameEventObserver {
    private val levelFilePaths = mutableListOf<String>()
    private var currentLevelIndex = 0
    lateinit var gameView: GameView
    lateinit var inventoryView: InventoryView
    lateinit var map: Map

    init {
        //Retrieve files from assets.
        levelFilePaths.add("level1.json")
        levelFilePaths.add("level2.json")
        EventManager.subscribe(this)
    }

    override fun onGameEvent(event: GameEvent) {
        when (event) {
            is GameEvent.LevelSucceedEvent -> {
                if (currentLevelIndex < (levelFilePaths.size - 1)) {
                    unloadMap()
                    currentLevelIndex++
                    loadMap(levelFilePaths[currentLevelIndex])
                    updateView()
                    updateInventory()
                } else {
                    unloadMap()
                    EventManager.notify(GameEvent.GameFinished)
                }

            }
            is GameEvent.LevelFailedEvent -> {
                unloadMap()
                EventManager.unsubscribe(gameView)
                EventManager.unsubscribe(inventoryView)
                EventManager.notify(GameEvent.GameFinished)
            }
            else -> {}
        }
    }

    /**
     * Updates the view
     */
    fun updateView() {
        gameView = GameView(context, this)
        EventManager.subscribe(gameView) //Make gameView reactive to the game events.
        container.removeAllViews()
        container.addView(gameView)
    }

    /**
     * Updates the inventory
     */
    fun updateInventory() {
        inventoryView = InventoryView(context, this)
        EventManager.subscribe(inventoryView) //Make gameView reactive to the game events.
        inventoryContainer.removeAllViews()
        inventoryContainer.addView(inventoryView)
    }

    /**
     * Loads a new map and subscribes it to the EventManager.
     */
    fun loadMap(filePath: String) {
        map = Map(context, filePath)
        EventManager.subscribe(map)
    }

    /**
     * Unloads the map by unsubscribing to the EventManager.
     */
    fun unloadMap() {
        EventManager.unsubscribe(map)
    }

    fun start() {
        //Start the first level.
        loadMap(levelFilePaths[0])

        gameView = GameView(context, this)
        inventoryView = InventoryView(context,this)

        EventManager.subscribe(gameView) //Make gameView reactive to the game events.
        EventManager.subscribe(inventoryView)

        container.addView(gameView)
        inventoryContainer.addView(inventoryView)
    }
}