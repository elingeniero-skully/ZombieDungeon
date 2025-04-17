package com.example.labyrinthe
import java.io.File
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import android.content.Context
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject

class Map(context: Context, fileNameInAssets: String) : GameEventObserver {
    val maxSize: Vector2D //Maximum size of the map (will be initialized in the init block)
    val objectsOnTheMap = mutableListOf<GameObject>() //Only Entity or Wall : they are drawable and have a position.
    var player: Player

    /**
     * Builds the map from the input file filePath.
     * For now, errors are not handled.
     */
    init {
        //Parse the map file
        val jsonString = loadLevelFromAssets(context, fileNameInAssets)
        val mapData: MapData = Json.decodeFromString<MapData>(jsonString)

        //Retrieve the map size. The empty cases are simply not represented for performance purposes.
        maxSize = Vector2D(mapData.width, mapData.height)

        //Unpack into objectsOnTheMap
        for (case in mapData.cases) {
            val parser = JsonParserFactory.getParser(case.type)
            objectsOnTheMap.add(parser.parse(case) as GameObject) //Polymorphism in action !
        }

        player = findObjectOfType<Player>()
    }

    override fun onGameEvent(event: GameEvent) {
        when (event) {
            is GameEvent.PlayerMoveRequest -> {
                when(event.direction) {
                    "rotate left" -> {
                        player.rotateLeft()
                        EventManager.notify(GameEvent.RenderEvent)
                    }
                    "rotate right" -> {
                        player.rotateRight()
                        EventManager.notify(GameEvent.RenderEvent)
                    }
                    "up" -> {
                        //Collision check in the sight direction.
                        val nextObject = checkCollisionForward(player.position, Vector2D.fromSightDirection(player.sightDirection))

                        if (nextObject is Door && nextObject.unlocked) {
                            EventManager.notify(GameEvent.LevelSucceedEvent)
                        } else if (nextObject == null) {
                            player.moveForward()
                            EventManager.notify(GameEvent.RenderEvent)
                        }
                    }
                    "down" -> {
                        //Collision check in the sight direction.
                        val nextObject = checkCollisionBackward(player.position, Vector2D.fromSightDirection(player.sightDirection))

                        if (nextObject is Door && nextObject.unlocked) {
                            EventManager.notify(GameEvent.LevelSucceedEvent)
                        } else if (nextObject == null) {
                            player.moveBackward()
                            EventManager.notify(GameEvent.RenderEvent)
                            EventManager.notify(GameEvent.BossKilledEvent)
                        }
                    }
                }
            }

            is GameEvent.BossKilledEvent -> {
                val door = findObjectOfType<Door>()
                door.unlocked = true
                EventManager.notify(GameEvent.RenderEvent)
            }

            else -> {}
        }
    }

    /**
     * Read a file from the assets/ folder.
     */
    fun loadLevelFromAssets(context: Context, filename: String): String {
        val inputStream = context.assets.open(filename)
        return inputStream.bufferedReader().use { it.readText() }
    }

    /**
     * Returns the object that has a certain position on the map.
     * If there's none, the function will return null. The built-in method find makes the code very compact and readable.
     * The implementation is tricky : the object is a GameObject BUT because only Walls, Mobs and Players (no items) are in objectsOnTheMap,
     * The object is guaranteed to implement Drawable so it must have the position attribute. That is why the it is Drawable expression is used.
     */
    fun getElementByPosition(position: Vector2D): GameObject? {
        return objectsOnTheMap.find { it is Drawable && it.position == position }
    }

    /**
     * Returns the first instance of specified type from the objectsOnTheMap.
     */
    inline fun <reified T : GameObject> findObjectOfType(): T {
        return objectsOnTheMap.filterIsInstance<T>().first()
    }

    /**
     * Returns the first object in the collision line.
     */
    fun checkInlineCollision(startingPosition: Vector2D, rawDirection: Vector2D): GameObject? {
        val direction = rawDirection.keepSignOnly()
        var position = startingPosition

        for (i in 0 until maxSize.maxOf()) {
            position += direction
            val nextElement = getElementByPosition(position)
            if (nextElement != null) {
                return nextElement
            }
        }
        return null //Out of bounds
    }

    /**
     * Checks surrounding position collision.
     */
    fun checkCollisionForward(startingPosition: Vector2D, rawDirection: Vector2D): GameObject? {
        //Check for out of bounds
        val newPosition = startingPosition + rawDirection.keepSignOnly()
        if (newPosition.x < maxSize.x && newPosition.x >= 0 && newPosition.y < maxSize.y && newPosition.y >= 0) {
            return getElementByPosition(newPosition)
        }

        return Wall(newPosition) //A trick, the Wall will NOT be added to the map.
    }

    fun checkCollisionBackward(startingPosition: Vector2D, rawDirection: Vector2D): GameObject? {
        //Check for out of bounds
        val newPosition = startingPosition - rawDirection.keepSignOnly()
        if (newPosition.x < maxSize.x && newPosition.x >= 0 && newPosition.y < maxSize.y && newPosition.y >= 0) {
            return getElementByPosition(newPosition)
        }

        return Wall(newPosition)
    }
}