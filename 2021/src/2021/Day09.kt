package `2021`

import java.io.File

fun main() {
    val input =
        File(ClassLoader.getSystemResource("2021/input09.txt").toURI()).readLines()
            .map { it.split("").filter { it.isNotEmpty() }.map(String::toInt) }

    var part1 = 0
    val baisinSizes = mutableListOf<Int>()
    (input.indices).forEach { y ->
        (input[y].indices).map { x ->
            val north = if (y - 1 >= 0) input[y - 1][x] else 9
            val middle = input[y][x]
            val east = if (x + 1 < input[y].size) input[y][x + 1] else 9
            val west = if (x - 1 >= 0) input[y][x - 1] else 9
            val south = if (y + 1 < input.size) input[y + 1][x] else 9

            if (north > middle && east > middle && west > middle && south > middle) {
                part1 += middle + 1
                val baisinList = mutableListOf(Pair(x, y))
                (input.indices).forEach {
                    val newBaisins = baisinList.distinct().map { (x, y) ->
                        val tempList = mutableListOf<Pair<Int, Int>>()
                        if (y - 1 >= 0) {
                            if (input[y - 1][x] != 9) {
                                tempList.add(Pair(x, y - 1))
                            }
                        }
                        if (x + 1 < input[y].size) {
                            if (input[y][x + 1] != 9) {
                                tempList.add(Pair(x + 1, y))
                            }
                        }
                        if (x - 1 >= 0) {
                            if (input[y][x - 1] != 9) {
                                tempList.add(Pair(x - 1, y))
                            }
                        }
                        if (y + 1 < input.size) {
                            if (input[y + 1][x] != 9) {
                                tempList.add(Pair(x, y + 1))
                            }
                        }
                        tempList
                    }.flatten()
                    baisinList.addAll(newBaisins)
                }
                baisinSizes.add(baisinList.distinct().size)
            }
        }
    }
    println("Part1: $part1")
    baisinSizes.sort()
    val part2 = baisinSizes.asReversed().take(3).reduce { acc, size -> acc * size }
    println("Part2: $part2")
}