package `2023`

import java.io.File

fun main() {

    //val inputs = File(ClassLoader.getSystemResource("2023/dummy13.txt").toURI()).readLines()
    val inputs = File(ClassLoader.getSystemResource("2023/input13.txt").toURI()).readLines()
    Day13.part1(inputs)
    Day13.part2(inputs)
}

object Day13 {

    // 34699 - too low
    // 35014 - too low
    // 38437 - nope
    // 40148 - nope
    fun part1(inputs: List<String>) {
        val puzzles = inputs.joinToString("\n").split("\n\n").map { it.split("\n") }
        val res = puzzles.mapIndexed { index, puzzle ->
            val horizontalIndexes = checkForMirrorIndex(puzzle)
            val transposedPuzzle = Helper.transpose(puzzle)
            val verticalIndexes = checkForMirrorIndex(transposedPuzzle)
            verticalIndexes.sum() + horizontalIndexes.map { it * 100 }.sum()
        }.sum()
        println(res)
    }

    private fun checkForMirrorIndex(puzzle: List<String>): List<Int> {
        val indizes = mutableListOf<Int>()
        puzzle.indices.forEach puzzle@{ index ->
            if (index == 0) return@puzzle
            val beforeLine = puzzle[index - 1]
            val thisLine = puzzle[index]
            if (thisLine == beforeLine) {
                var count = 1
                var up = beforeLine
                var down = thisLine
                while (up == down && (index - 1 - count >= 0) && (index + count < puzzle.size)) {
                    up = puzzle[index - 1 - count]
                    down = puzzle[index + count]
                    if (up == down) count++
                }
                if (up == down) indizes.add(index)
            }
        }
        return indizes
    }

    fun part2(inputs: List<String>) {

    }

}