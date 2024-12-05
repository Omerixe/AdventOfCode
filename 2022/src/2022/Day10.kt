package `2022`

import java.io.File

fun main() {

    val input = File(ClassLoader.getSystemResource("2022/input10.txt").toURI())
        .readLines()

    Day10.part1(input)
    Day10.part2(input)
}

object Day10 {
    /*
        0 1 - after first
        1 1 - after second
        2 1 - after third
        3 4 - after fourth
        4 4 - after fifth
        5 -1 - after sixths

        1 = after cycle 1
        41 = after cycle 41
     */

    private fun goThroughCycles(input: List<String>): List<Int> {
        return input.map { it.split(" ") }
            .fold(mutableListOf(1)) { acc, element ->
                val instruction = element[0]
                val currentVal = acc.last()
                if (instruction == "noop") {
                    acc.add(currentVal)
                } else {
                    val value = element[1].toInt()
                    acc.add(currentVal)
                    acc.add(currentVal + value)
                }
                acc
            }
    }

    fun part1(input: List<String>) {
        val cycles = listOf(20, 60, 100, 140, 180, 220)
        val result = goThroughCycles(input).mapIndexed { idx, element ->
            if (cycles.contains(idx + 1)) {
                (idx + 1) * element
            } else {
                0
            }
        }
        println("Part1: ${result.sum()}")
    }

    fun part2(input: List<String>) {
        println("Part2:")
        goThroughCycles(input).mapIndexed { idx, sprite ->
            val startSprite = sprite - 1
            val endSprite = sprite + 1
            // idx 40 = cycle 41
            if ((idx).mod(40) in startSprite..endSprite) {
                "#"
            } else {
                "."
            }
        }.dropLast(1)
            .chunked(40)
            .map { it.chunked(5).joinToString(" ") { it.joinToString("") } }
            .forEach { println(it) }
    }
}
