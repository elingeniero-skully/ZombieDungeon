package com.example.labyrinthe

import kotlin.math.abs

/**
 * Defines the random movement pattern
 */
class RandomMovementPattern : MovementPattern {
    override fun behave(mob: Mob, map: Map) {
        val distanceToPlayer: Vector2D = map.player.position - mob.position

        if (distanceToPlayer.normCeil() > 1) {

            //Only four directions allowed
            val direction = listOf(Vector2D(1,0),Vector2D(0,1), Vector2D(-1,0), Vector2D(0,-1)).random()

            //Adjust the sight direction accordingly
            when(direction) {
                Vector2D(1,0) -> mob.sightDirection = 0
                Vector2D(0,1) -> mob.sightDirection = 1
                Vector2D(-1,0) -> mob.sightDirection = 2
                Vector2D(0,-1) -> mob.sightDirection = 3
            }

            map.move(mob, "up")

        } else {
            //If the mob is 1 block away from the player

            //Is the player one diagonal case away from the mob ?
            val isDiagonal = (abs(distanceToPlayer.x) == abs(distanceToPlayer.y))

            var direction: Vector2D

            if (distanceToPlayer.maxOfAbs() == abs(distanceToPlayer.x)) { //Direction as (x,0)
                if (isDiagonal) {
                    direction = listOf(Vector2D(distanceToPlayer.keepSignOnly().x, 0), Vector2D(0,distanceToPlayer.keepSignOnly().y)).random()
                } else {
                    direction = Vector2D(distanceToPlayer.keepSignOnly().x, 0)
                }

            } else { //Direction as (0,y)
                direction = Vector2D(0,distanceToPlayer.keepSignOnly().y)
            }

            //Adjust the sight direction
            when(direction) {
                Vector2D(1,0) -> mob.sightDirection = 0
                Vector2D(0,1) -> mob.sightDirection = 1
                Vector2D(-1,0) -> mob.sightDirection = 2
                Vector2D(0,-1) -> mob.sightDirection = 3
            }

            //Attack the player.
            map.hurt(map.player, (mob.inventory.first() as Weapon).damage)
        }
    }
}