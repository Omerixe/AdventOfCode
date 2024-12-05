package `2023`

import java.io.File

fun main() {
    //val inputs = File(ClassLoader.getSystemResource("2023/dummy06.txt").toURI()).readLines()

    val inputs = File(ClassLoader.getSystemResource("2023/input06.txt").toURI()).readLines()
    Day06.part1(inputs)
    Day06.part2(inputs)
}

object Day06 {
    fun part1(inputs: List<String>) {
        val times = inputs.first().replace("Time: ", "").split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val distances = inputs.last().replace("Distance: ", "").split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val combination = times.zip(distances)
        val result = combination.map { (time, distance) ->
            val possibleDurations = mutableListOf<Int>()
            for (duration in 1 until time) {
                val effeciveTime = time - duration
                val result = effeciveTime * duration
                if (result > distance) possibleDurations.add(duration)
            }
            possibleDurations
        }.map { it.size }.fold(1) { acc, next -> acc * next }
        println("Part 1: $result")
    }

    fun part2(inputs: List<String>) {
        val time = inputs.first().replace("Time: ", "").filter { it.isDigit() }.toLong()
        val distance = inputs.last().replace("Distance: ", "").filter { it.isDigit() }.toLong()

        var count = 0
        for (duration in 1 until time) {
            val effeciveTime = time - duration
            val result = effeciveTime * duration
            if (result > distance) count++
        }
        println(count)
    }
}
