package com.example.labyrinthe
import kotlin.math.*

data class Vector2D(var x: Int, var y:Int) {

    companion object {
        /**
         * Returns a direction vector from a sight direction
         */
        fun fromSightDirection(sightDirection: Int): Vector2D {
            return when (sightDirection) {
                0 -> Vector2D(1, 0)   // right
                1 -> Vector2D(0, 1)   // down
                2 -> Vector2D(-1, 0)  // left
                3 -> Vector2D(0, -1)  // up
                else -> Vector2D(0, 0)
            }
        }
    }

    /** Allows to do a + b. */
    operator fun plus(other: Vector2D): Vector2D {
        return Vector2D(this.x + other.x, this.y + other.y)
    }

    /** Allows to do a - b. */
    operator fun minus(other: Vector2D): Vector2D {
        return Vector2D(this.x - other.x, this.y - other.y)
    }

    /** Returns a new vector with only the sign. Ex : (-4,5) => (-1,1).
     * It is useful to define the 2D directions. There are 8 theoretical but 4 in practice because diagonal movements are not allowed.
     */
    fun keepSignOnly(): Vector2D {
        return Vector2D(x.sign,y.sign)
    }

    /** Normalizes the vector. */
    fun normalize() {
        val norm = sqrt(x.toDouble().pow(2) + y.toDouble().pow(2))
        x = (x.toDouble() / norm).toInt()
        y = (y.toDouble() / norm).toInt()
    }

    /** Norm of the vector. **/
    fun norm(): Int {
        return sqrt(x.toDouble().pow(2) + y.toDouble().pow(2)).roundToInt()
    }

    /** Ceil norm of the vector. **/
    fun normCeil(): Int {
        return ceil(sqrt(x.toDouble().pow(2) + y.toDouble().pow(2))).toInt()
    }

    /** Rotates the vector by a certain angle in degrees represented by an Int. */
    fun rotate(angleInDegrees: Int) {
        val angleInRads = angleInDegrees * PI/180
        val X = (x.toDouble()*cos(angleInRads) - y.toDouble()*sin(angleInRads)).roundToInt()
        val Y = (x.toDouble()*sin(angleInRads) + y.toDouble()*cos(angleInRads)).roundToInt()
        x = X
        y = Y
    }

    /** Returns the maximum value between the x and y coordinates. */
    fun maxOf(): Int {
        return maxOf(x,y)
    }

    /** Returns the maximum value between the x and y coordinates in absolute value. */
    fun maxOfAbs(): Int {
        return maxOf(abs(x),abs(y))
    }
}
