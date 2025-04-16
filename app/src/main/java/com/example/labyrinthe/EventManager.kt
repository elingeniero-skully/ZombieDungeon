package com.example.labyrinthe

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
