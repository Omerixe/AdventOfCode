package `2024`

import java.io.File
import kotlin.math.abs

fun main() {
    //val inputs = File(ClassLoader.getSystemResource("2024/dummy02.txt").toURI()).readLines()

    val inputs = File(ClassLoader.getSystemResource("2024/input02.txt").toURI()).readLines()

    Day02.part1(inputs)
    Day02.part2(inputs)
}

object Day02 {
    fun part1(input: List<String>) {
        val result = input.map { line ->
            line.split(" ").map { it.toInt() }
        }.map { line ->
            line.windowed(2).map { it[0] - it[1] }
        }.map { diffList ->
            diffList.all { abs(it) in 1..3 } && (diffList.all { it > 0 } || diffList.all { it < 0 })
        }.filter { it }.size
        println(result)
    }

    fun part2(input: List<String>) {
        val result = input.map { line ->
            line.split(" ").map { it.toInt() }
        }.map { line ->
            List(line.size) { index ->
                line.filterIndexed { i, _ -> i != index }
            } + listOf(line)
        }.map { listOfLines ->
            listOfLines
                .map { line ->
                    line.windowed(2).map { it[0] - it[1] }
                }.map { diffList ->
                    diffList.all { abs(it) in 1..3 } && (diffList.all { it > 0 } || diffList.all { it < 0 })
                }.any { it }
        }.filter { it }.size

        println(result)
    }
}
