package `2024`

import java.io.File

fun main() {
    //val inputs = File(ClassLoader.getSystemResource("2024/dummy10.txt").toURI()).readLines()

    val inputs = File(ClassLoader.getSystemResource("2024/input10.txt").toURI()).readLines()

    Day10.part1(inputs)
    Day10.part2(inputs)
}

object Day10 {
    fun part1(input: List<String>) {
        val paddedInput = input.addPadding('.')
        val result = paddedInput.flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, char ->
                if (char == '0') {
                    Pair(x, y)
                } else {
                    null
                }
            }
        }.sumOf {
            findTrails(paddedInput, it, 0).toSet().size
        }
        println(result)
    }

    fun part2(input: List<String>) {
        val paddedInput = input.addPadding('.')
        val result = paddedInput.flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, char ->
                if (char == '0') {
                    Pair(x, y)
                } else {
                    null
                }
            }
        }.sumOf {
            findTrails(paddedInput, it, 0).size
        }
        println(result)
    }

    private fun findTrails(
        input: List<String>,
        currentPosition: Pair<Int, Int>,
        currentHeight: Int
    ): List<Pair<Int, Int>> {
        if (currentHeight == 9) {
            return listOf(currentPosition)
        }
        return currentPosition.getStraightSurroundingElements(input)
            .filter { it.first != '.' && it.first.toString().toInt() == currentHeight + 1 }.flatMap { newPath ->
                findTrails(input, newPath.second, currentHeight + 1)
            }
    }

}
