package `2023`

import java.io.File
import kotlin.math.pow

fun main() {
    val dummyInput = """
Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
    """.trimIndent()

    //val inputs = dummyInput.split("\n")

    val inputs = File(ClassLoader.getSystemResource("2023/input04.txt").toURI()).readLines()
    Day04.part1(inputs)
    Day04.part2(inputs)
}

object Day04 {
    fun part1(inputs: List<String>) {
        val res = inputs.map { line ->
            val card = line.split(":").last().split("|")
            val winningNumbers = card.first().split(" ").filter { it.isNotBlank() }.map { it.toInt() }
            val havingNumbers = card.last().split(" ").filter { it.isNotBlank() }.map { it.toInt() }
            val intersection = winningNumbers.intersect(havingNumbers).size.toDouble()
            if (intersection > 0) 2.0.pow(intersection - 1) else 0.0

        }.sum().toInt()
        println(res)
    }

    fun part2(inputs: List<String>) {
        val res = inputs.foldIndexed(IntArray(inputs.size) { 1 }) { idx, acc, line ->
            val card = line.split(":").last().split("|")
            val winningNumbers = card.first().split(" ").filter { it.isNotBlank() }.map { it.toInt() }
            val havingNumbers = card.last().split(" ").filter { it.isNotBlank() }.map { it.toInt() }
            val matchingNumbers = winningNumbers.intersect(havingNumbers).size
            for (diff in 1..matchingNumbers) {
                acc[idx + diff] += acc[idx]
            }
            acc
        }.sum()
        println(res)
    }
}
