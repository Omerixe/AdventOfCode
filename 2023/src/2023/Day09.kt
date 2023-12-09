package `2023`

import java.io.File

fun main() {
    val dummyInput = """
0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45
    """.trimIndent()

    //val inputs = dummyInput.split("\n")

    val inputs = File(ClassLoader.getSystemResource("2023/input09.txt").toURI()).readLines()
    Day09.part1(inputs)
    Day09.part2(inputs)
}

object Day09 {
    fun part1(inputs: List<String>) {
        val res = inputs.map { it.split(" ").map(String::toInt) }.map { line ->
            val allLines = mutableListOf(line)
            while (allLines.last().filterNot { it == 0 }.isNotEmpty()) {
                val nextLine = allLines.last().windowed(2).map { window ->
                    window.last() - window.first()
                }
                allLines.add(nextLine)
            }
            allLines.reversed().map { it.last() }.fold(0) { acc, number ->
                acc + number
            }
        }.sum()
        println(res)
    }

    fun part2(inputs: List<String>) {
        val res = inputs.map { it.split(" ").map(String::toInt) }.map { line ->
            val allLines = mutableListOf(line)
            while (allLines.last().filterNot { it == 0 }.isNotEmpty()) {
                val nextLine = allLines.last().windowed(2).map { window ->
                    window.last() - window.first()
                }
                allLines.add(nextLine)
            }
            allLines.reversed().map { it.first() }.fold(0) { acc, number ->
                number - acc
            }
        }
        println(res.sum())
    }
}