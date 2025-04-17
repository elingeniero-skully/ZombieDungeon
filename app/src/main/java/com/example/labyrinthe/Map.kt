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
    var mobs = listOf<Mob>()

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
        mobs = findObjectsOfType<Mob>()

    }

    override fun onGameEvent(event: GameEvent) {
        when (event) {
            is GameEvent.BossKilledEvent -> {
                val door = findObjectOfType<Door>()
                door.unlocked = true
                EventManager.notify(GameEvent.RenderEvent)
            }

            else -> {}
        }
    }

    fun move(entity: Entity, direction: String) {
        when(direction) {
            "rotate left" -> entity.rotateLeft()
            "rotate right" -> entity.rotateRight()
            "up" -> {
                //Collision check in the sight direction.
                val nextObject = checkCollisionForward(entity.position, Vector2D.fromSightDirection(entity.sightDirection))

                if (entity is Player) {
                    if (nextObject is Door && nextObject.unlocked) {
                        EventManager.notify(GameEvent.LevelSucceedEvent)
                    }
                }

                if (nextObject == null) {
                    entity.moveForward()
                }
            }
            "down" -> {
                //Collision check in the sight direction.
                val nextObject = checkCollisionBackward(entity.position, Vector2D.fromSightDirection(entity.sightDirection))

                if (entity is Player) {
                    if (nextObject is Door && nextObject.unlocked) {
                        EventManager.notify(GameEvent.LevelSucceedEvent)
                    }
                }

                if (nextObject == null) {
                    entity.moveBackward()
                }
            }
        }
    }

    /**
     * Inflict damage to an entity. If the player is dead, the LevelFailedEvent is sent.
     * If it is another entity, it is just removed from the map.
     */
    fun hurt(entity: Entity, amount: Int) {
        if (entity.health - amount <= 0) {
            entity.health = 0
            if (entity is Player) {
                EventManager.notify(GameEvent.LevelFailedEvent)
            } else if (entity is Boss){
                EventManager.notify(GameEvent.BossKilledEvent)
            }
            objectsOnTheMap.remove(entity)
            mobs = findObjectsOfType<Mob>()
        } else {
            entity.health -= amount
            if (entity is Player) {
                EventManager.notify(GameEvent.UpdateHealthEvent)
            }
        }
    }

    fun playerAttack() {
        val currentItem = (player.inventory[player.activeItem] as Weapon)

        val direction = when(player.sightDirection) {
            0 -> Vector2D(1,0)
            1 -> Vector2D(0,1)
            2 -> Vector2D(-1,0)
            3 -> Vector2D(0,-1)
            else -> Vector2D(0,0)
        }

        if (currentItem is Gun) {
            //Inline collision
            val collisionObject = checkInlineCollision(player.position, direction)
            if (collisionObject is Mob) {
                hurt(collisionObject, currentItem.damage)
                println("Player infliced ${currentItem.damage} to mob using ${currentItem.name}")
            }
        } else if (currentItem is Knife) {
            val collisionObject = checkCollisionForward(player.position, direction)
            if (collisionObject is Mob) {
                hurt(collisionObject, currentItem.damage)
                println("Player infliced ${currentItem.damage} to mob using ${currentItem.name}")
            }
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
     * Returns all instances of specified type from the objectsOnTheMap.
     */
    inline fun <reified T : GameObject> findObjectsOfType(): List<T> {
        return objectsOnTheMap.filterIsInstance<T>()
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