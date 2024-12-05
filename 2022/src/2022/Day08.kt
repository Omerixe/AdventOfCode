package `2022`

import java.io.File

fun main() {

    val input = File(ClassLoader.getSystemResource("2022/input08.txt").toURI())
        .readLines()

    Day08.part1(input)
    Day08.part2(input)
}

object Day08 {
    fun part1(input: List<String>) {
        val grid = input.map { it.trim().split("").filter { it.isNotBlank() } }
        var x: Int
        var y = 0
        var counter = 0
        grid.forEach { line ->
            x = 0
            line.forEach { cell ->
                if (x == 0 || y == 0 || x == line.size - 1 || y == line.size - 1) {
                    counter++
                } else {
                    var visible = true
                    //is visible from the left
                    (0 until x).forEach { tempX ->
                        if (grid[y][tempX] >= cell) {
                            visible = false
                        }
                    }
                    if (!visible) {
                        visible = true
                        //is visible from the right
                        (x + 1 until line.size).forEach { tempX ->
                            if (grid[y][tempX] >= cell) {
                                visible = false
                            }
                        }
                    }
                    if (!visible) {
                        visible = true
                        //is visible from top
                        (0 until y).forEach { tempY ->
                            if (grid[tempY][x] >= cell) {
                                visible = false
                            }
                        }
                    }
                    if (!visible) {
                        visible = true
                        //is visible from bottom
                        (y + 1 until grid.size).forEach { tempY ->
                            if (grid[tempY][x] >= cell) {
                                visible = false
                            }
                        }
                    }
                    if (visible) {
                        counter++
                    }
                }
                x++
            }
            y++
        }
        println("Part1: $counter")
    }

    fun part2(input: List<String>) {
        val grid = input.map { it.trim().split("").filter { it.isNotBlank() } }
        var x: Int
        var y = 0
        val result = grid.flatMap { line ->
            x = 0
            val results = line.map { cell ->
                var score = 1
                // count distance top
                var distance = 0
                var stop = false
                (y - 1 downTo 0).forEach { tempY ->
                    val tempCell = grid[tempY][x].toInt()

                    //Tree should be higher than the current tree - continue
                    if (!stop && tempCell < cell.toInt()) {
                        distance++
                    } else {
                        if (!stop) distance++
                        stop = true
                    }
                }
                score *= distance

                // count distance left
                distance = 0
                stop = false
                (x - 1 downTo 0).forEach { tempX ->
                    val tempCell = grid[y][tempX].toInt()
                    //Tree should be higher than the current tree - continue
                    if (!stop && tempCell < cell.toInt()) {
                        distance++
                    } else {
                        if (!stop) distance++
                        stop = true
                    }
                }
                score *= distance

                // count distance bottom
                distance = 0
                stop = false
                (y + 1 until grid.size).forEach { tempY ->
                    val tempCell = grid[tempY][x].toInt()
                    //Tree should be higher than the current tree - continue
                    if (!stop && tempCell < cell.toInt()) {
                        distance++
                    } else {
                        if (!stop) distance++
                        stop = true
                    }
                }
                score *= distance

                // count distance right
                distance = 0
                stop = false
                (x + 1 until line.size).forEach { tempX ->
                    val tempCell = grid[y][tempX].toInt()
                    //Tree should be higher than the current tree - continue
                    if (!stop && tempCell < cell.toInt()) {
                        distance++
                    } else {
                        if (!stop) distance++
                        stop = true
                    }
                }
                score *= distance
                x += 1
                score
            }
            y++
            results
        }
        println("Part2: ${result.max()}")
    }
}
