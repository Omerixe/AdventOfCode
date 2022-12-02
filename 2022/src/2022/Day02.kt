package `2022`

import java.io.File

class Day02 {
    fun main() {
        val dummyInput = """
 A Y
 B X
 C Z
    """.trimIndent()

        //val numbers = dummyInput.split("\n")

        val numbers = File(ClassLoader.getSystemResource("2022/input02.txt").toURI())
            .readLines()

        part1(numbers)
        part2(numbers)
    }

    fun part1(numbers: List<String>) {
        val output = numbers.map { pair ->
            val new = pair.split(" ")
            val shape =
                when (new[1]) {
                    "X" -> 1
                    "Y" -> 2
                    "Z" -> 3
                    else -> 0
                }

            val win = when {
                new[0] == "A" && new[1] == "X" -> 3
                new[0] == "A" && new[1] == "Y" -> 6
                new[0] == "B" && new[1] == "Y" -> 3
                new[0] == "B" && new[1] == "Z" -> 6
                new[0] == "C" && new[1] == "Z" -> 3
                new[0] == "C" && new[1] == "X" -> 6
                else -> 0
            }
            shape + win
        }
        println("Part1: ${output.sum()}")
    }

    fun part2(numbers: List<String>) {
        val output = numbers.map { pair ->
            val new = pair.split(" ")

            // A Rock, B Paper, C Scissorc
            // X lose, Y draw, Z win
            // 1 Rock, 2 Paper, 3 Scisscors
            when {
                new[0] == "A" && new[1] == "X" -> 0 + 3
                new[0] == "A" && new[1] == "Y" -> 3 + 1
                new[0] == "A" && new[1] == "Z" -> 6 + 2
                new[0] == "B" && new[1] == "Y" -> 3 + 2
                new[0] == "B" && new[1] == "Z" -> 6 + 3
                new[0] == "B" && new[1] == "X" -> 0 + 1
                new[0] == "C" && new[1] == "Z" -> 6 + 1
                new[0] == "C" && new[1] == "X" -> 0 + 2
                new[0] == "C" && new[1] == "Y" -> 3 + 3
                else -> 0
            }

        }
        println("Part2: ${output.sum()}")
    }
}
