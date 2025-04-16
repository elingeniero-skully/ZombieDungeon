package com.example.labyrinthe
import java.io.File
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import android.content.Context
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject

class Map(context: Context, fileNameInAssets: String) {
    val maxSize: Vector2D //Maximum size of the map (will be initialized in the init block)
    val objectsOnTheMap = mutableListOf<GameObject>() //Only Entity or Wall : they are drawable and have a position.

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
            val parser = JsonParserFactory.getParser(case.type)
            objectsOnTheMap.add(parser.parse(case) as GameObject) //Polymorphism in action !
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
     * Returns the Player object
     */
    fun getPlayerObject(): Player {
        return objectsOnTheMap.filterIsInstance<Player>().first()
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