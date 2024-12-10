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

/**
 *    N1
 * N2 N0 N3
 *    N4
 * Where N0 is the given starting point in the middle by x and y
 * Result: North = 0, West = 1, East = 2, South = 3
 */
fun Pair<Int, Int>.getStraightSurroundingElements(map: List<String>): List<Pair<Char, Pair<Int, Int>>> {
    val x = this.x
    val y = this.y
    val result = mutableListOf<Pair<Char, Pair<Int, Int>>>()
    result.add(Pair(map[y - 1][x], Pair(x, y - 1))) //N1
    result.add(Pair(map[y][x - 1], Pair(x - 1, y))) //N2
    result.add(Pair(map[y][x + 1], Pair(x + 1, y))) //N3
    result.add(Pair(map[y + 1][x], Pair(x, y + 1))) //N4
    return result
}

fun List<String>.addPadding(paddingChar: Char): List<String> {
    val lineLength = this.first().length
    val paddingLine = listOf(paddingChar.toString().repeat(lineLength + 2))
    return paddingLine + this.map { ".$it." } + paddingLine
}