package `2022`

import java.io.File

fun main() {
    val input = File(ClassLoader.getSystemResource("2022/input14.txt").toURI()).readLines()

    Day14.part1(input)
    Day14.part2(input)
}

object Day14 {

    fun part1(input: List<String>) {
        val cave = initializeCave(input)
        val startPoint = Pair(500, 0)
        val bottomLine = cave.map { it.key.second }.max()
        var overflow = false

        while (!overflow) {
            var currentX = startPoint.first
            var currentY = startPoint.second
            var stable = false
            while (!stable && !overflow) {
                if (cave[Pair(currentX, currentY + 1)] == null) {
                    if (currentY + 1 == bottomLine) overflow = true
                    currentY++
                } else if (cave[Pair(currentX - 1, currentY + 1)] == null) {
                    if (currentY + 1 == bottomLine) overflow = true
                    currentX--
                    currentY++
                } else if (cave[Pair(currentX + 1, currentY + 1)] == null) {
                    if (currentY + 1 == bottomLine) overflow = true
                    currentX++
                    currentY++
                } else {
                    cave[Pair(currentX, currentY)] = "o"
                    stable = true
                }

                /*(0..167).forEach { y ->
                    (450..513).forEach { x ->
                        if (cave.contains(Pair(x, y))) {
                            print(cave[Pair(x, y)])
                        } else {
                            print(".")
                        }
                    }
                    print("\n")
                }
                println()
                println()*/
            }
        }



        println("Part1: ${cave.filter { it.value == "o" }.size}")
    }

    private fun initializeCave(input: List<String>) =
        input.fold(mutableMapOf<Pair<Int, Int>, String>()) { acc, line ->
            line.split(" -> ").windowed(2).forEach {
                val first = it[0].split(",")
                val firstX = first.first().toInt()
                val firstY = first.last().toInt()
                val second = it[1].split(",")
                val secondX = second.first().toInt()
                val secondY = second.last().toInt()

                if (firstX == secondX) { //same x
                    val range = if (firstY < secondY) (firstY..secondY) else (secondY..firstY)
                    range.forEach {
                        acc[Pair(firstX, it)] = "#"
                    }
                } else { //same y
                    val range = if (firstX < secondX) (firstX..secondX) else (secondX..firstX)
                    range.forEach {
                        acc.put(Pair(it, firstY), "#")
                    }
                }
            }

            acc
        }

    fun part2(input: List<String>) {
        val cave = initializeCave(input)
        val startPoint = Pair(500, 0)
        val bottomLine = 2 + cave.map { it.key.second }.max()

        while (cave[startPoint] == null) {
            var currentX = startPoint.first
            var currentY = startPoint.second
            var stable = false
            while (!stable) {
                if (cave[Pair(currentX, currentY + 1)] == null && (currentY + 1 < bottomLine)) {
                    currentY++
                } else if (cave[Pair(currentX - 1, currentY + 1)] == null && (currentY + 1 < bottomLine)) {
                    currentX--
                    currentY++
                } else if (cave[Pair(currentX + 1, currentY + 1)] == null && (currentY + 1 < bottomLine)) {
                    currentX++
                    currentY++
                } else {
                    cave[Pair(currentX, currentY)] = "o"
                    stable = true
                }

                /*
                /*(0..167).forEach { y -> */
                (0..12).forEach { y ->
                    //(450..513).forEach { x ->
                    (485..520).forEach { x ->
                        if (cave.contains(Pair(x, y))) {
                            print(cave[Pair(x, y)])
                        } else {
                            print(".")
                        }
                    }
                    print("\n")
                }
                println()
                println()*/
            }
        }
        println("Part2: ${cave.filter { it.value == "o" }.size}")
    }
}
