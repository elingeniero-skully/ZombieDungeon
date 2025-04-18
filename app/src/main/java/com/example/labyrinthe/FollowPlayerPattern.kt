package com.example.labyrinthe

import kotlin.math.abs

/**
 * If the player is more than one case away from the mob,
 * See which movement reduces the distance the most.
 * If the player is exactly one case away from the mob (not diagonal ones !), then it attacks the player.
 */
class FollowPlayerPattern : MovementPattern {
    override fun behave(mob: Mob, map: Map) {
        val distanceToPlayer = map.player.position - mob.position

        val isDiagonal = (abs(distanceToPlayer.x) == abs(distanceToPlayer.y))

        //Follow the player
        var direction: Vector2D

        //Find the direction in which to go
        if (distanceToPlayer.maxOfAbs() == abs(distanceToPlayer.x)) {
            //Direction as (x,0)
            if (isDiagonal) {
                direction = listOf(Vector2D(distanceToPlayer.keepSignOnly().x, 0), Vector2D(0,distanceToPlayer.keepSignOnly().y)).random()
            } else {
                direction = Vector2D(distanceToPlayer.keepSignOnly().x, 0)
            }
        } else {
            //Direction as (0,y)
            direction = Vector2D(0,distanceToPlayer.keepSignOnly().y)
        }

        //Adjust the sight direction
        when(direction) {
            Vector2D(1,0) -> mob.sightDirection = 0
            Vector2D(0,1) -> mob.sightDirection = 1
            Vector2D(-1,0) -> mob.sightDirection = 2
            Vector2D(0,-1) -> mob.sightDirection = 3
        }

        if (distanceToPlayer.normCeil() > 1) {
            map.move(mob, "up")
        } else {
            map.hurt(map.player, (mob.inventory.first() as Weapon).damage)
        }
    }
}