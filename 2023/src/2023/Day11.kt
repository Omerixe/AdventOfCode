package `2023`

import java.io.File
import kotlin.math.abs

fun main() {

    //val inputs = File(ClassLoader.getSystemResource("2023/dummy11.txt").toURI()).readLines()
    val inputs = File(ClassLoader.getSystemResource("2023/input11.txt").toURI()).readLines()
    Day11.part1(inputs)
    Day11.part2(inputs)
}

object Day11 {

    fun part1(inputs: List<String>) {
        val transposedOriginal = Helper.transpose(inputs)
        val emptyLines = inputs.mapIndexed { index, line ->
            if (line.replace(".", "").isBlank()) {
                index
            } else {
                -1
            }
        }.filter { it > 0 }
        val emptyColumns = transposedOriginal.mapIndexed { index, line ->
            if (line.replace(".", "").isBlank()) {
                index
            } else {
                -1
            }
        }.filter { it > 0 }
        val finalMap = inputs.flatMapIndexed { index, line ->
            val newLine = emptyColumns.foldIndexed(line) { index, acc, column ->
                acc.substring(0, column + index + 1) + acc.substring(column + index)
            }
            if (emptyLines.contains(index)) {
                listOf(newLine, newLine)
            } else {
                listOf(newLine)
            }
        }

        val galaxies = finalMap.flatMapIndexed { y, line ->
            val allOcc = line.mapIndexed { index, char ->
                if (char == '#') index else -1
            }.filter { it >= 0 }
            allOcc.map { Pair(y, it) }
        }
        val distance = galaxies.flatMapIndexed { index, g1 ->
            (index + 1 until galaxies.size).map { g2Index ->
                val g2 = galaxies[g2Index]
                val yDiff = abs(g2.first - g1.first)
                val xDiff = abs(g2.second - g1.second)
                yDiff + xDiff
            }
        }.sum()
        println(distance)
    }


    fun part2(inputs: List<String>) {
        val times = 1000000L
        val transposedOriginal = Helper.transpose(inputs)
        val emptyLines = inputs.mapIndexed { index, line ->
            if (line.replace(".", "").isBlank()) {
                index
            } else {
                -1
            }
        }.filter { it > 0 }
        val emptyColumns = transposedOriginal.mapIndexed { index, line ->
            if (line.replace(".", "").isBlank()) {
                index
            } else {
                -1
            }
        }.filter { it > 0 }

        val galaxies = inputs.flatMapIndexed { y, line ->
            val allOcc = line.mapIndexed { index, char ->
                if (char == '#') index else -1
            }.filter { it >= 0 }
            allOcc.map { Pair(y, it) }
        }

        val distance = galaxies.flatMapIndexed { index, g1 ->
            (index + 1 until galaxies.size).map { g2Index ->
                val g2 = galaxies[g2Index]
                val yDiff = abs(g2.first - g1.first)
                val xDiff = abs(g2.second - g1.second)

                val yOccCount = emptyLines.filter { it > g1.first && it < g2.first }.size
                val xOccCount =
                    emptyColumns.filter { it > g1.second && it < g2.second || it > g2.second && it < g1.second }.size
                xDiff - xOccCount + yDiff - yOccCount + xOccCount * times + yOccCount * times
            }
        }.sum()
        println(distance)
    }

}