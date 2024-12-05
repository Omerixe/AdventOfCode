package `2023`

import java.io.File
import kotlin.math.pow

fun main() {
    //val inputs = File(ClassLoader.getSystemResource("2023/dummy04.txt").toURI()).readLines()

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
