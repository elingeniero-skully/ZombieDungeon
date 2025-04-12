package com.example.labyrinthe
import java.io.File
import kotlinx.serialization.*
import kotlinx.serialization.json.Json

/**
 * Defines different types of tiles on the map (used for (un)serialization of the map).
 */
@Serializable
enum class TileType { WALL, PLAYER, MOB }

/**
 * Defines the tile on the map (used for (un)serialization of the map).
 */
@Serializable
data class MapCase(val x: Int, val y: Int, val type: TileType)

/**
 * Base structure in which a map is (un)serialized.
 */
@Serializable
data class MapData(
    val width: Int,
    val height: Int,
    val cases: List<MapCase>
)

class Map(val filePath: String) {
    val maxSize: Vector2D //Maximum size of the map (will be initialized in the init block)
    val objectsOnTheMap = mutableListOf<GameObject>() //Only Entity or Wall : they are drawable and have a position.

    /**
     * Builds the map from the input file filePath.
     * For now, errors are not handled.
     */
    init {
        //Don't forget to include serialization lib dependency and do the import

        //1) Parse the map file
        val jsonString = File(filePath).readText()
        val mapData: MapData = Json.decodeFromString<MapData>(jsonString)

        //2) Retrieve the map size. The empty cases are simply not represented for performance purposes.
        maxSize = Vector2D(mapData.width, mapData.height)

        //3) Unpack into objectsOnTheMap
        for (case in mapData.cases) {
            when (case.type) {
                TileType.WALL   -> objectsOnTheMap.add(Wall(Vector2D(case.x, case.y)))
                TileType.MOB    -> objectsOnTheMap.add(Mob(Vector2D(case.x, case.y)))
                TileType.PLAYER -> objectsOnTheMap.add(Player(Vector2D(case.x, case.y)))
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