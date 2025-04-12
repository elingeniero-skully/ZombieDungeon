package com.example.labyrinthe

class Map(val filePath: String) {
    var maxSize: Vector2D = Vector2D(255,255) //Maximum size of the map (can be updated in the init block)
    val objectsOnTheMap = mutableListOf<GameObject>()

    /**
     * Builds the map from the input file filePath.
     */
    init {
        TODO("Implement how the map is loaded from a file.")
    }

    /**
     * Returns the object that has a certain position on the map.
     * If there's none, the function will return null. The built-in method find makes the code very compact and readable.
     */
    fun getElementByPosition(position: Vector2D): GameObject? {
        return objectsOnTheMap.find { it.position == position }
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