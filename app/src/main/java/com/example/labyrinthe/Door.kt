package com.example.labyrinthe

import android.graphics.Color
import android.graphics.Paint
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Door(position: Vector2D): Wall(position) {
    var unlocked = false
    companion object {
        var paint = Paint().apply{
            color = Color.YELLOW
            style = Paint.Style.FILL
        }
        var paintUnlocked = Paint().apply{
            color = Color.GREEN
            style = Paint.Style.FILL
        }
    }
}