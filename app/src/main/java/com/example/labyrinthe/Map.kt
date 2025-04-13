package com.example.labyrinthe
import java.io.File
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import android.content.Context

/**
 * Defines different types of tiles on the map (used for (un)serialization of the map).
 */
@Serializable
enum class TileType { WALL, DOOR, PLAYER, MOB, BOSS }

/**
 * Defines the tile on the map (used for (un)serialization of the map).
 */
@Serializable
data class MapCase(
    val x: Int,
    val y: Int,
    val type: TileType,
    val args: List<String> //Arguments to pass to the constructor.
)

/**
 * Base structure in which a map is (un)serialized.
 */
@Serializable
data class MapData(
    val width: Int,
    val height: Int,
    val cases: List<MapCase>
)

class Map(private val context: Context, val fileNameInAssets: String) {
    val maxSize: Vector2D //Maximum size of the map (will be initialized in the init block)
    val objectsOnTheMap = mutableListOf<GameObject>() //Only Entity or Wall : they are drawable and have a position.

    /**
     * Read a file from the assets/ folder.
     */
    fun loadLevelFromAssets(context: Context, filename: String): String {
        val inputStream = context.assets.open(filename)
        return inputStream.bufferedReader().use { it.readText() }
    }

    /**
     * Builds the map from the input file filePath.
     * For now, errors are not handled.
     */
    init {
        //1) Parse the map file
        //val jsonString = File(filePath).readText()
        val jsonString = loadLevelFromAssets(context, fileNameInAssets)
        val mapData: MapData = Json.decodeFromString<MapData>(jsonString)

        //2) Retrieve the map size. The empty cases are simply not represented for performance purposes.
        maxSize = Vector2D(mapData.width, mapData.height)

        //3) Unpack into objectsOnTheMap
        for (case in mapData.cases) {
            when (case.type) {
                TileType.WALL   -> {
                    //CONSTRUCTOR : Wall(positionArg: Vector2D)
                    objectsOnTheMap.add(Wall(Vector2D(case.x, case.y)))
                }
                TileType.DOOR   -> {
                    //CONSTRUCTOR : Door(position: Vector2D)
                    objectsOnTheMap.add(Door(Vector2D(case.x, case.y)))
                }
                TileType.MOB    -> {
                    //CONSTRUCTOR : Mob(positionArg: Vector2D, movementPattern: MovementPattern = RandomMovementPattern())
                    val position: Vector2D = Vector2D(case.x, case.y)
                    var movementPattern: Mob.MovementPattern = Mob.RandomMovementPattern()

                    //Converts the String argument into a movementPattern. Must be explained thoroughly in the docs.
                    //args[0] = first constructor argument; args[n] = n-1 constructor argument.
                    //This way of coding is not very modular but it works.
                    when (case.args[0]) {
                        "random"   -> movementPattern = Mob.RandomMovementPattern()
                        "follow"   -> movementPattern = Mob.FollowPlayerPattern()
                        "line"     -> movementPattern = Mob.LineMovementPattern()
                        "circular" -> movementPattern = Mob.CircularMovementPattern()
                    }
                    objectsOnTheMap.add(Mob(position, movementPattern))
                }
                TileType.BOSS    -> {
                    //CONSTRUCTOR : BOSS(positionArg: Vector2D, movementPattern: MovementPattern = RandomMovementPattern())
                    val position: Vector2D = Vector2D(case.x, case.y)
                    var movementPattern: Mob.MovementPattern = Mob.RandomMovementPattern()

                    //Converts the String argument into a movementPattern. Must be explained thoroughly in the docs.
                    //args[0] = first constructor argument; args[n] = n-1 constructor argument.
                    //This way of coding is not very modular but it works.
                    when (case.args[0]) {
                        "random"   -> movementPattern = Mob.RandomMovementPattern()
                        "follow"   -> movementPattern = Mob.FollowPlayerPattern()
                        "line"     -> movementPattern = Mob.LineMovementPattern()
                        "circular" -> movementPattern = Mob.CircularMovementPattern()
                    }
                    objectsOnTheMap.add(Boss(position, movementPattern))
                }
                TileType.PLAYER -> {
                    objectsOnTheMap.add(Player(Vector2D(case.x, case.y)))
                }
            }
        }
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
     * Checks if there's something next to a specified position, in a certain direction.
     */
    fun checkNextCollision(startingPosition: Vector2D, rawDirection: Vector2D): GameObject? {
        return getElementByPosition(startingPosition + rawDirection.keepSignOnly())
    }
}