package com.example.labyrinthe

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * The JsonParser lets each class provide its way of parsing data from a .json file.
 */
abstract class JsonParser {
    /**
     * Extract the data from the json file into a data structure.
     * Build an object from the data structure and then return it.
     */
    abstract fun parse(mapCase: MapCase): Drawable
}
