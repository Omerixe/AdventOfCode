package `2024`

import kotlin.math.sqrt

fun String.splitIntoPair(delimiter: String): Pair<String, String> =
    split(delimiter, limit = 2).chunked(2).map { Pair(it[0], it[1]) }.first()

operator fun List<String>.get(pos: Pair<Int, Int>): Char = this[pos.y][pos.x]
val Pair<Int, Int>.y: Int
    get() = second
val Pair<Int, Int>.x: Int
    get() = first

fun Pair<Int, Int>.distance(other: Pair<Int, Int>): Double {
    val x1 = this.x * 1.0
    val y1 = this.y * 1.0
    val x2 = other.x * 1.0
    val y2 = other.y * 1.0
    return sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1))
}

fun Pair<Int, Int>.slope(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.x - other.x, this.y - other.y)
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> = Pair(this.x + other.x, this.y + other.y)

operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> = Pair(this.x - other.x, this.y - other.y)
operator fun Pair<Int, Int>.times(times: Int): Pair<Int, Int> = Pair(this.x * times, this.y * times)