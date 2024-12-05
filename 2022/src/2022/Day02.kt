package `2022`

import java.io.File

fun main() {

    val numbers = File(ClassLoader.getSystemResource("2022/input02.txt").toURI())
        .readLines()

    Day02.part1(numbers)
    Day02.part2(numbers)
}

object Day02 {

    fun part1(numbers: List<String>) {
        // Version 1
        /*val output = numbers.map { pair ->
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
        }*/

        val output = numbers.map {
            when (it) {
                "A X" -> 3 + 1
                "A Y" -> 6 + 2
                "A Z" -> 0 + 3
                "B X" -> 0 + 1
                "B Y" -> 3 + 2
                "B Z" -> 6 + 3
                "C X" -> 6 + 1
                "C Y" -> 0 + 2
                "C Z" -> 3 + 3
                else -> 0
            }
        }
        println("Part1: ${output.sum()}")
    }

    fun part2(numbers: List<String>) {
        // Version 1
        /*val output = numbers.map { pair ->
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

        }*/
        val output = numbers.map { pair ->
            // A Rock, B Paper, C Scissorc
            // X lose, Y draw, Z win
            // 1 Rock, 2 Paper, 3 Scisscors
            when (pair) {
                "A X" -> 0 + 3
                "A Y" -> 3 + 1
                "A Z" -> 6 + 2
                "B Y" -> 3 + 2
                "B Z" -> 6 + 3
                "B X" -> 0 + 1
                "C Z" -> 6 + 1
                "C X" -> 0 + 2
                "C Y" -> 3 + 3
                else -> 0
            }

        }
        println("Part2: ${output.sum()}")
    }
}
