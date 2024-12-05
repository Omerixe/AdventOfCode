package `2024`

import java.io.File

fun main() {
    //val inputs = File(ClassLoader.getSystemResource("2024/dummy05.txt").toURI()).readLines()

    val inputs = File(ClassLoader.getSystemResource("2024/input05.txt").toURI()).readLines()

    Day05.part1(inputs)
    Day05.part2(inputs)
}

object Day05 {
    fun part1(input: List<String>) {
        val result = input.partition { it.contains("|") }.run {
            val rules = first.map { it.splitIntoPair("|") }
            second.filter { it.isNotBlank() }.map { line ->
                val lineNumbers = line.split(",")
                lineNumbers.mapIndexed { firstIndex, number ->
                    rules.filter { it.first == number }.map { rule ->
                        val secondPos = lineNumbers.indexOf(rule.second).run { if (this < 0) Int.MAX_VALUE else this }
                        firstIndex < secondPos
                    }.all { it }
                }.all { it }.run {
                    if (this) {
                        lineNumbers[lineNumbers.size / 2].toInt()
                    } else {
                        0
                    }
                }
            }.sum()
        }
        println(result)
    }

    fun part2(input: List<String>) {
        val result = input.partition { it.contains("|") }.run {
            val rules = first.map { it.splitIntoPair("|") }
            second.filter { it.isNotBlank() }.mapNotNull { line ->
                val lineNumbers = line.split(",")
                lineNumbers.mapIndexed { firstIndex, number ->
                    rules.filter { it.first == number }.map { rule ->
                        val secondPos = lineNumbers.indexOf(rule.second).run { if (this < 0) Int.MAX_VALUE else this }
                        firstIndex < secondPos
                    }.all { it }
                }.run {
                    if (contains(false)) {
                        lineNumbers.sortedWith { str1: String, str2: String ->
                            if (rules.any { it.first == str1 && it.second == str2 }) -1 else 1
                        }
                    } else {
                        null
                    }
                }
            }.sumOf {
                it[it.size / 2].toInt()
            }
        }
        println(result)
    }


}
