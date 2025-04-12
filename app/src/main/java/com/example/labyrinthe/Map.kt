package com.example.labyrinthe

class Map {
    //Maximum size of the map
    val maxSize = Vector2D(255,255)
    val objectsOnTheMap = mutableListOf<GameObject>()

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