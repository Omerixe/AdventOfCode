package `2023`

import java.io.File

fun main() {

    //val inputs = File(ClassLoader.getSystemResource("2023/dummy12.txt").toURI()).readLines()
    val inputs = File(ClassLoader.getSystemResource("2023/input12.txt").toURI()).readLines()
    Day12.part1(inputs)
    Day12.part2(inputs)
}

object Day12 {

    fun part1(inputs: List<String>) {
        val result = inputs.map { line ->
            val (springs, controlGroup) = line.toPair(" ")
            val controlGroups = controlGroup.split(",").map { it.toInt() }
            val springGroups = springs.groupBy { it }
            // find all possible solutions
            val possiblesolutions = replaceAll(springs, "?", listOf("#", "."))
            // check each solution if it fits
            possiblesolutions.map { solution ->
                val checks = getResultingDamagedSpringGroup(solution)
                checks == controlGroups
            }.count { it }
        }.sum()
        println(result)
    }

    private fun replaceAll(currentString: String, occurence: String, with: List<String>): List<String> {
        // check if there are any occurences left
        if (!currentString.contains(occurence)) return listOf(currentString)
        // replace next occurence
        return with.flatMap { char ->
            val newVersion = currentString.replaceFirst(occurence, char)
            replaceAll(newVersion, occurence, with)
        }
    }

    private fun getResultingDamagedSpringGroup(currentString: String): List<Int> {
        return currentString.fold(mutableListOf(0)) { acc, char ->
            if (char == '.' && acc.last() != 0) {
                acc.add(0)
            } else if (char == '#') {
                val index = acc.size - 1
                acc[index] = acc[index] + 1
            }
            acc
        }.filter { it != 0 }
    }

    fun String.toPair(separator: String): Pair<String, String> {
        val parts = split(separator)
        return Pair(parts[0], parts[1])
    }

    fun part2(inputs: List<String>) {

    }

}