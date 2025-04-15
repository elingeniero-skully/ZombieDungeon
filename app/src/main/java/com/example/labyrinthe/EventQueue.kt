package com.example.labyrinthe

/**
 * The internal queue contains events that are being processed in the current "tick".
 * The external queue contains "external" events such as a key press.
 */
class EventQueue {
    private val internalQueue: ArrayDeque<GameEvent> = ArrayDeque()
    private val externalQueue: ArrayDeque<GameEvent> = ArrayDeque()

    fun enqueueExternal(event: GameEvent) {
        externalQueue.add(event)
    }

    fun enqueueInternal(event: GameEvent) {
        internalQueue.add(event)
    }

    fun dispatchAll(listeners: List<GameEventListener>) {
        // Puts external events into the internal queue.
        internalQueue.addAll(externalQueue)
        externalQueue.clear()

        while (internalQueue.isNotEmpty()) {
            val event = internalQueue.removeFirst()
            listeners.forEach { it.onEvent(event, this) }
        }
    }
}