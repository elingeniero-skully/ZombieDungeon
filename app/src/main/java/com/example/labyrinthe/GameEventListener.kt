package com.example.labyrinthe

/**
 * Classes that implement GameEventListener must provide a way of handling events through the onEvent() method.
 * E.g. The Player class could react to a PLayerMove event by requesting a move to the map.
 */
interface GameEventListener {
    /**
     * Events called from the onEvent method *must* be queued in the internal queue.
     */
    fun onEvent(event: GameEvent, queue: EventQueue)
}
