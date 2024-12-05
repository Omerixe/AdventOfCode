package `2022`

import java.io.File

fun main() {

    val input = File(ClassLoader.getSystemResource("2022/input03.txt").toURI())
        .readLines()

    Day03.part1(input)
    Day03.part2(input)
}

object Day03 {

    fun part1(input: List<String>) {
        val map = (('a'..'z').map { it }.zip(1..26) + ('A'..'Z').map { it }.zip(27..52)).toMap()
        // Version 1
        /*al result: List<Int> = input.map { line ->
            val first = line.subSequence(0, (line.length / 2)).toMutableList()
            val second = line.subSequence(startIndex = line.length / 2, endIndex = line.length).toList()
            first.retainAll(second)
            map[first.first().toChar()]!!
        }*/

        val result =
            input.map { Pair(it.subSequence(0, it.length / 2), it.subSequence(it.length / 2, it.length)) }
                .map { it.first.toList().intersect(it.second.toList()) }
                .map { it.first() }
                .map { map[it]!! }
                .sum()

        println("Part1: ${result}")
    }

    fun part2(input: List<String>) {
        val map = (('a'..'z').map { it }.zip(1..26) + ('A'..'Z').map { it }.zip(27..52)).toMap()
        // Version 1
        /*val groups = input.chunked(3)
        val result = groups.map {
            val first = it[0].toMutableList()
            val second = it[1].toMutableList()
            second.retainAll(it[2].toList())
            first.retainAll(second)
            map[first.first()]!!
        }.sum()*/

        val result = input.chunked(3)
            .map { it[0].toList().intersect(it[1].toList()).intersect(it[2].toList()) }
            .map { it.first() }
            .map { map[it]!! }
            .sum()
        println("Part2: $result")
    }
}
