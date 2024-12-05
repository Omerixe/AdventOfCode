package `2022`

import java.io.File

fun main() {


    val input = File(ClassLoader.getSystemResource("2022/input06.txt").toURI())
        .readLines()

    Day06.part1(input)
    Day06.part2(input)
}

object Day06 {
    fun part1(input: List<String>) {
        val res = input.map {
            it.windowed(4)
                .map { element ->
                    element.toSet().size == 4
                }.indexOfFirst { it } + 4
        }

        println("Part1: $res")
    }

    fun part2(input: List<String>) {
        val res = input.map {
            it.windowed(14)
                .map { element ->
                    element.toSet().size == 14
                }.indexOfFirst { it } + 14
        }

        println("Part2: $res")
    }
}
