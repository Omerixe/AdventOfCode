package `2022`

import java.io.File
import kotlin.math.abs

fun main() {

    val input = File(ClassLoader.getSystemResource("2022/input09.txt").toURI())
        .readLines()

    Day09.part1(input)
    Day09.part2(input)
}

object Day09 {
    fun moveTail(x: Int, y: Int, current: Pair<Int, Int>): Pair<Int, Int> {
        val newX = if (abs(current.first - x) == 2) {
            if (current.first > x) current.first - 1 else current.first + 1
        } else current.first

        val newY = if (abs(current.second - y) == 2) {
            if (current.second > y) current.second - 1 else current.second + 1
        } else current.second

        return if (x == current.first && y == current.second) {
            current
        } else if (x == current.first) { //same column
            Pair(x, newY)
        } else if (y == current.second) { //same row
            Pair(newX, y)
        } else { // diagonal
            if (abs(current.second - y) == 2 || abs(current.first - x) == 2) {
                val xli = if (current.first > x) current.first - 1 else current.first + 1
                val yli = if (current.second > y) current.second - 1 else current.second + 1
                Pair(xli, yli)
            } else {
                Pair(newX, newY)
            }
        }
    }

    fun part1(input: List<String>) {
        var x = 0
        var y = 0
        var lastPair = Pair(0, 0)
        val result = input.map { it.split(" ") }.flatMap { command ->
            val direction = command[0]
            val amount = command[1].toInt()

            (0 until amount).map {
                when (direction) {
                    "U" -> y++
                    "R" -> x++
                    "L" -> x--
                    "D" -> y--
                    else -> {}
                }
                //move Tail
                val newPair = moveTail(x, y, lastPair)
                lastPair = newPair
                newPair
            }

        }
        println("Part1: ${result.toSet().size}")
    }

    fun part2(input: List<String>) {
        var x = 0
        var y = 0
        var knots = (1..10).map { Pair(0, 0) }.toMutableList()
        val result = input.map { it.split(" ") }.flatMap { command ->
            val direction = command[0]
            val amount = command[1].toInt()

            (0 until amount).map {
                when (direction) {
                    "U" -> y++
                    "R" -> x++
                    "L" -> x--
                    "D" -> y--
                    else -> {}
                }
                knots.set(0, Pair(x, y))
                val newKnots = knots.foldIndexed(mutableListOf(Pair(x, y))) { idx, acc, knot ->
                    if (idx == 0) {
                        acc
                    } else {
                        val knotBefore = acc.last()
                        val newKnot = moveTail(knotBefore.first, knotBefore.second, knot)
                        acc.add(newKnot)
                        acc
                    }
                }
                knots = newKnots
                newKnots[9]
            }

        }
        println("Part2: ${result.toSet().count()}")
    }
}
