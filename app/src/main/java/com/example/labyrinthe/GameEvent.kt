package com.example.labyrinthe

sealed class GameEvent {
    /**
     * Event that triggers a MapUpdateEvent inside the Map class.
     */
    class PlayerMoveRequest(val direction: String): GameEvent()
    object ShowInventory : GameEvent()
    object LevelSucceedEvent : GameEvent()
    object LevelFailedEvent : GameEvent()

    /**
     * Event that triggers the rendering of the GameView.
     * Use it with moderation as the whole map is rendered again !
     */
    object RenderEvent: GameEvent()

    /**
     * Event that triggers the door unlocking.
     */
    object BossKilledEvent: GameEvent()

    object GameFinished: GameEvent()

    object SwitchActiveItemEvent : GameEvent()
}
