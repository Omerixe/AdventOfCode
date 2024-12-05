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
            countPossibilities(springs, "?", listOf("#", "."), controlGroups)
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

    private fun countPossibilities(
        currentString: String,
        occurence: String,
        with: List<String>,
        checklist: List<Int>
    ): Long {
        // check if there are any occurences left
        if (!currentString.contains(occurence)) {
            val checks = getResultingDamagedSpringGroup(currentString)
            return if (checks == checklist) 1L else 0L
        }
        // replace next occurence
        return with.map { char ->
            val newVersion = currentString.replaceFirst(occurence, char)
            val damagedSpringGroup = getResultingDamagedSpringGroupUntilFirstOcc(newVersion, '?')
            if (damagedSpringGroup.isEmpty() || damagedSpringGroup.isNotEmpty() && checklist.take(damagedSpringGroup.size - 1) == damagedSpringGroup.take(
                    damagedSpringGroup.size - 1
                )
            ) {
                countPossibilities(newVersion, occurence, with, checklist)
            } else {
                0
            }
        }.sum()
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

    private fun getResultingDamagedSpringGroupUntilFirstOcc(currentString: String, occurence: Char): List<Int> {
        return currentString.fold(mutableListOf(0)) { acc, char ->
            if (char == occurence) return acc.filter { it != 0 }
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
        val result = inputs.map { line ->
            val (springs, controlGroup) = line.toPair(" ")
            val newSprings = "$springs?$springs?$springs?$springs?$springs"
            val newControlGroup = "$controlGroup,$controlGroup,$controlGroup,$controlGroup,$controlGroup"
            val controlGroups = newControlGroup.split(",").map { it.toInt() }
            // find all possible solutions
            countPossibilities(newSprings, "?", listOf("#", "."), controlGroups)
        }.sum()
        println(result)
    }

}