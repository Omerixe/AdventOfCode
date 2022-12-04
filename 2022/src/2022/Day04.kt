package `2022`

import java.io.File

fun main() {
    val dummyInput = """
2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8
    """.trimIndent()

    //val input = dummyInput.split("\n")

    val input = File(ClassLoader.getSystemResource("2022/input04.txt").toURI())
        .readLines()

    Day04.part1(input)
    Day04.part2(input)
}

object Day04 {

    fun part1(input: List<String>) {
        val res = input
            .map {
                it.split(",")
                    .map { it.split("-") }
                    .map {
                        (it[0].toInt()..it[1].toInt()).toList()
                    }
            }.map {
                it[0].containsAll(it[1]) || it[1].containsAll(it[0])
            }.count { it }

        println("Part1: $res ")
    }

    fun part2(input: List<String>) {
        val res = input
            .map {
                it.split(",")
                    .map { it.split("-") }
                    .map {
                        (it[0].toInt()..it[1].toInt()).toList()
                    }
            }.map {
                it[0].intersect(it[1])
            }.count { it.isNotEmpty() }

        println("Part2: $res")
    }
}
