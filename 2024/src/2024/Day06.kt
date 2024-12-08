package `2024`

import java.io.File

fun main() {
    //val inputs = File(ClassLoader.getSystemResource("2024/dummy06.txt").toURI()).readLines()

    val inputs = File(ClassLoader.getSystemResource("2024/input06.txt").toURI()).readLines()

    Day06.part1(inputs)
    Day06.part2(inputs)
}
object Day06 {
    fun part1(input: List<String>) {
        val start = input.mapIndexedNotNull { index, line ->
            if (line.contains("^")) {
                val xPos = line.indexOf("^")
                Pair(xPos, index)
            } else null
        }.first()

        println(calculateSteps(input, start)?.size)
    }

    fun part2(input: List<String>) {
        val start = input.mapIndexedNotNull { index, line ->
            if (line.contains("^")) {
                val xPos = line.indexOf("^")
                Pair(xPos, index)
            } else null
        }.first()

        val original = calculateSteps(input, start)

        // Brute-force solution ¯\_(ツ)_/¯
        val result = original!!.filter { it != start }.map {
            val newInput = input.toMutableList()
            newInput[it.y] = newInput[it.y].replaceRange(it.x..it.x, "#")
            calculateSteps(newInput, start)
        }.filter { it == null }.size
        println(result)
    }

    private fun calculateSteps(input: List<String>, start: Pair<Int, Int>): Set<Pair<Int, Int>>? {
        var fellOut = false
        var foundLoop = false
        var currentPos = start
        var currentDir = input[start]
        val visitedPositions = mutableSetOf<Pair<Int, Int>>()
        val loopCheck = mutableSetOf<Pair<Int, Int>>()
        while (!fellOut && !foundLoop) {
            val new = visitedPositions.add(currentPos)
            if (!new) {
                loopCheck.add(currentPos)
                if (loopCheck.size > 1 && loopCheck.first() == currentPos) {
                    foundLoop = true
                    continue
                }
            } else {
                loopCheck.removeAll { true }
            }
            when (currentDir) {
                '^' -> {
                    // check above
                    val nextPos = Pair(currentPos.x, currentPos.y - 1)
                    if (nextPos.y >= 0) {
                        if (input[nextPos] == '#') {
                            currentDir = '>'
                        } else {
                            currentPos = nextPos
                        }
                    } else {
                        fellOut = true
                    }
                }

                '>' -> {
                    // check right
                    val nextPos = Pair(currentPos.x + 1, currentPos.y)
                    if (nextPos.x < input.first().length) {
                        if (input[nextPos] == '#') {
                            currentDir = 'v'
                        } else {
                            currentPos = nextPos
                        }
                    } else {
                        fellOut = true
                    }
                }

                '<' -> {
                    // check left
                    val nextPos = Pair(currentPos.x - 1, currentPos.y)
                    if (nextPos.x >= 0) {
                        if (input[nextPos] == '#') {
                            currentDir = '^'
                        } else {
                            currentPos = nextPos
                        }
                    } else {
                        fellOut = true
                    }
                }

                'v' -> {
                    // check below
                    val nextPos = Pair(currentPos.x, currentPos.y + 1)
                    if (nextPos.y < input.size) {
                        if (input[nextPos] == '#') {
                            currentDir = '<'
                        } else {
                            currentPos = nextPos
                        }
                    } else {
                        fellOut = true
                    }
                }
            }
        }
        return if (foundLoop) null else visitedPositions
    }
}
