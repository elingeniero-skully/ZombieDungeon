package com.example.labyrinthe

/**
 * The EventManager is involved in the signalling of the events to the relevant observers
 * as prescribed by the observer pattern.
 */
object EventManager {
    private val observers = mutableListOf<GameEventObserver>()

    fun subscribe(observer: GameEventObserver) {
        observers.add(observer)
    }

    fun unsubscribe(observer: GameEventObserver) {
        observers.remove(observer)
    }

    fun notify(event: GameEvent) {
        for (observer in observers.toList()) {
            observer.onGameEvent(event)
        }
    }
}
