package `2024`

import java.io.File
import kotlin.math.abs

fun main() {
    //val inputs = File(ClassLoader.getSystemResource("2024/dummy01.txt").toURI()).readLines()

    val inputs = File(ClassLoader.getSystemResource("2024/input01.txt").toURI()).readLines()

    Day01.part1(inputs)
    Day01.part2(inputs)
}

object Day01 {
    fun part1(input: List<String>) {
        val result = input.map { line ->
            line.splitIntoPair("   ")
        }.unzip().run {
            first.sorted().zip(second.sorted())
        }.sumOf { abs(it.first.toInt() - it.second.toInt()) }

        println(result)
    }

    fun part2(input: List<String>) {
        val result = input.map { line ->
            line.splitIntoPair("   ")
        }.unzip().run {
            first.map { firstLocation -> second.filter { firstLocation == it }.size * firstLocation.toInt() }
        }.sum()
        println(result)
    }
}
