package `2023`

import java.io.File

fun main() {

    val inputs = File(ClassLoader.getSystemResource("2023/dummy13.txt").toURI()).readLines()
    //val inputs = File(ClassLoader.getSystemResource("2023/input13.txt").toURI()).readLines()
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
            val horizontalIndexes = checkForMirrorIndexes(puzzle)
            val transposedPuzzle = Helper.transpose(puzzle)
            val verticalIndexes = checkForMirrorIndexes(transposedPuzzle)
            verticalIndexes.sum() + horizontalIndexes.map { it * 100 }.sum()
        }.sum()
        println(res)
    }

    private fun checkForMirrorIndexes(puzzle: List<String>): List<Int> {
        val indizes = mutableListOf<Int>()
        puzzle.indices.forEach puzzle@{ index ->
            if (checkConsecutiveLines(lineIndex = index, puzzle = puzzle)) {
                indizes.add(index)
            }
        }
        return indizes
    }

    private fun checkConsecutiveLines(lineIndex: Int, puzzle: List<String>, offset: Int = 0): Boolean {
        if (lineIndex == 0) return false
        var count = offset
        var up = puzzle[lineIndex - 1 - count]
        var down = puzzle[lineIndex + count]
        while (up == down && (lineIndex - 1 - count + 1 >= 0) && (lineIndex + count + 1 < puzzle.size)) {
            count++
            up = puzzle[lineIndex - 1 - count]
            down = puzzle[lineIndex + count]
        }
        if (up == down) return true

        return false
    }

    fun part2(inputs: List<String>) {
        val puzzles = inputs.joinToString("\n").split("\n\n").map { it.split("\n") }
        val res = puzzles.mapIndexed { index, puzzle ->


            //val horizontalIndexes = checkForMirrorIndex(puzzle)
            //val transposedPuzzle = Helper.transpose(puzzle)
            //val verticalIndexes = checkForMirrorIndex(transposedPuzzle)
            //verticalIndexes.sum() + horizontalIndexes.map { it * 100 }.sum()
            0
        }.sum()
        println(res)
    }

    private fun checkForSmudges(puzzle: List<String>): List<Int> {
        val smudges = mutableListOf<Int>()
        puzzle.indices.forEach puzzle@{ index ->
            if (index == 0) return@puzzle
            var count = 0
            var up = puzzle[index - 1 - count]
            var down = puzzle[index + count]
            while (up == down && (index - 1 - count + 1 >= 0) && (index + count + 1 < puzzle.size)) {
                count++
                up = puzzle[index - 1 - count]
                down = puzzle[index + count]
            }
            if (up == down) smudges.addAll(listOf(index + count, index - 1 - count))
        }
        return smudges
    }

    private fun String.differenceCount(otherString: String): Int {
        //We now just assume the same length
        return this.foldIndexed(0) { index, acc, char ->
            if (char == otherString[index]) acc + 1 else acc
        }
    }

}