package com.example.labyrinthe

import kotlinx.serialization.Serializable

/**
 * Doesn't implement Drawable because items will not be shown directly on the map.
 * They will only be accessible through an inventory.
 */
abstract class Item: GameObject() {

}