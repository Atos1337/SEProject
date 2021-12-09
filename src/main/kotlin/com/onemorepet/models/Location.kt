package com.onemorepet.models

import kotlin.math.sqrt

data class Location(
    val x: Double,
    val y: Double
) {
    fun distance(other: Location): Double = sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y))
}
