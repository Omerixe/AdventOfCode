package `2024`

import java.io.File

fun main() {
    //val inputs = File(ClassLoader.getSystemResource("2024/dummy07.txt").toURI()).readLines()

    val inputs = File(ClassLoader.getSystemResource("2024/input07.txt").toURI()).readLines()

    Day07.part1(inputs)
    Day07.part2(inputs)
}

object Day07 {
    fun part1(input: List<String>) {
        val operators = listOf<(Long, Long) -> Long>(Long::plus, Long::times)
        val result = input.map { line ->
            line.split(":", " ").filterNot { it.isBlank() }.run {
                val expectedResult = this[0].toLong()
                this.drop(2).fold(listOf<Long>(this[1].toLong())) { acc, elem ->
                    acc.flatMap { res -> operators.map { it(res, elem.toLong()) } }
                }.filter { it == expectedResult }
            }
        }.filter { it.isNotEmpty() }.sumOf { it.first() }
        println(result)
    }

    fun part2(input: List<String>) {
        val operators = listOf<(Long, Long) -> Long>(
            Long::plus,
            Long::times,
            { left, right -> (left.toString() + right.toString()).toLong() })
        val result = input.map { line ->
            line.split(":", " ").filterNot { it.isBlank() }.run {
                val expectedResult = this[0].toLong()
                this.drop(2).fold(listOf<Long>(this[1].toLong())) { acc, elem ->
                    acc.flatMap { res -> operators.map { it(res, elem.toLong()) } }
                }.filter { it == expectedResult }
            }
        }.filter { it.isNotEmpty() }.sumOf { it.first() }
        println(result)
    }


}
