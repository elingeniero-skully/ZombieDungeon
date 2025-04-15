package com.example.labyrinthe


/**
 * Main superclass that represents a game event.
 * Android events that are relevant will be handled too as game events.
 */
abstract class GameEvent {
}

//Some examples of events.
class KeyPressedEvent(val keyCode: Int) : GameEvent()
class PlayerMovedEvent(val dx: Int, val dy: Int) : GameEvent()

/**
 * The event queue is a singleton that contains all the events yet to be processed.
 */
object EventQueue {
    private val queue = mutableListOf<GameEvent>()

    fun push(event: GameEvent) {
        queue.add(event)
    }

    fun pollAll(): List<GameEvent> {
        val events = queue.toList()
        queue.clear()
        return events
    }
}
