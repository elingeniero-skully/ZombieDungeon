package com.example.labyrinthe


/**
 * Main superclass that represents a game event.
 * Android events that are relevant will be handled too as game events.
 */
abstract class GameEvent {
}

//Some examples of events.
class KeyPressedEvent(val keyCode: Int) : GameEvent()

/**
 * The internal queue contains events that are being processed in the current "tick".
 * The external queue contains "external" events such as a key press.
 */
class EventQueue {
    private val internalQueue: ArrayDeque<GameEvent> = ArrayDeque()
    private val externalQueue: ArrayDeque<GameEvent> = ArrayDeque()
    private var locked = false

    fun enqueueExternal(event: GameEvent) {
        if (!locked) externalQueue.add(event)
    }

    fun enqueueInternal(event: GameEvent) {
        internalQueue.add(event)
    }

    fun dispatchAll(listeners: List<GameEventListener>) {
        locked = true

        // Puts external events into the internal queue.
        internalQueue.addAll(externalQueue)
        externalQueue.clear()

        while (internalQueue.isNotEmpty()) {
            val event = internalQueue.removeFirst()
            listeners.forEach { it.onEvent(event, this) }
        }

        locked = false
    }
}
