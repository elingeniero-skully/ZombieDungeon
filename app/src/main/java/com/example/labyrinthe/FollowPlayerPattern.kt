package com.example.labyrinthe

import kotlin.math.abs

/**
 * If the player is more than one case away from the mob,
 * See which movement reduces the distance the most.
 * If the player is exactly one case away from the mob (not diagonal ones !), then it attacks the player.
 */
class FollowPlayerPattern : MovementPattern {
    override fun behave(mob: Mob, map: Map) {
        val distanceToPlayerVect = map.player.position - mob.position

        val isDiagonal = (abs(distanceToPlayerVect.x) == abs(distanceToPlayerVect.y))

        //Follow the player
        var directionToGo = Vector2D(0,0)

        //Find the direction in which to go
        if (distanceToPlayerVect.maxOfAbs() == abs(distanceToPlayerVect.x)) {
            //Direction as (x,0)
            if (isDiagonal) {
                val directions = listOf(Vector2D(distanceToPlayerVect.keepSignOnly().x, 0), Vector2D(0,distanceToPlayerVect.keepSignOnly().y))
                directionToGo = directions.random() //If there's a wall blocking the way.
            } else {
                directionToGo = Vector2D(distanceToPlayerVect.keepSignOnly().x, 0)
            }
        } else {
            //Direction as (0,y)
            directionToGo = Vector2D(0,distanceToPlayerVect.keepSignOnly().y)
        }

        //Adjust the sight direction
        when(directionToGo) {
            Vector2D(1,0) -> mob.sightDirection = 0
            Vector2D(0,1) -> mob.sightDirection = 1
            Vector2D(-1,0) -> mob.sightDirection = 2
            Vector2D(0,-1) -> mob.sightDirection = 3
        }

        if (distanceToPlayerVect.normCeil() > 1) {
            map.moveMob(mob, "up")
        } else {
            map.hurt(map.player, (mob.inventory.first() as Weapon).damage)
            println("Mob infliced ${(mob.inventory.first() as Weapon).damage} to Player using ${(mob.inventory.first() as Weapon).name}")
        }


    }
}