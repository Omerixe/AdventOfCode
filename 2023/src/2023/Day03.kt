package `2023`

import java.io.File

fun main() {
    //val inputs = File(ClassLoader.getSystemResource("2023/dummy03.txt").toURI()).readLines()

    val inputs = File(ClassLoader.getSystemResource("2023/input03.txt").toURI()).readLines()
    Day03.part1(inputs)
    Day03.part2(inputs)
}

object Day03 {
    //530369 too high
    //526821 too high
    //526518 too high
    fun part1(inputs: List<String>) {
        val paddedInput = Helper.addPaddingTo2DList(inputs, '.')

        val resultList = mutableListOf<Pair<Int, Char>>()

        paddedInput.forEachIndexed { y, line ->
            val numberRegex = Regex("\\d+")
            val allNumbers = numberRegex.findAll(line).map { it.value }.toSet()

            //Find start of each number (attention, a number can occur multiple times in one line)
            allNumbers.forEach { number ->
                val starts = line.indexOfAll(number)
                val signs = starts.map { numberStart ->
                    val results = (numberStart until (numberStart + number.length)).map { x ->
                        Helper.getAllSurroundingElements(paddedInput, x, y).filterNot {
                            val character = it.first
                            character != null && character.isDigit() || character == '.'
                        }.map { it.first }
                    }.filter { it.isNotEmpty() }
                    if (results.isNotEmpty()) {
                        results.first().first()
                    } else null
                }

                signs.filterNotNull().forEach { resultList.add(Pair(number.toInt(), it)) }
            }
        }
        val sum = resultList.map { it.first }.sum()
        println("Part 1: $sum")
    }

    fun part2(inputs: List<String>) {
        val paddedInput = Helper.addPaddingTo2DList(inputs, '.')
        val multiplyMap = mutableMapOf<Pair<Int, Int>, List<Int>>()

        paddedInput.forEachIndexed { y, line ->
            val numberRegex = Regex("\\d+")
            val allNumbers = numberRegex.findAll(line).map { it.value }.toSet()

            //Find start of each number (attention, a number can occur multiple times in one line)
            allNumbers.forEach { number ->
                val starts = line.indexOfAll(number)
                val signs = starts.map { numberStart ->
                    val results = (numberStart until (numberStart + number.length)).map { x ->
                        Helper.getAllSurroundingElements(paddedInput, x, y).filterNot {
                            val character = it.first
                            character != null && character.isDigit() || character == '.'
                        }
                    }.filter { it.isNotEmpty() }
                    if (results.isNotEmpty()) {
                        val element = results.first().first()
                        if (element.first == '*') {
                            element.second
                        } else {
                            null
                        }
                    } else null
                }
                signs.filterNotNull().forEach { sign ->
                    if (multiplyMap.contains(sign)) {
                        multiplyMap[sign] = listOf(number.toInt()) + multiplyMap[sign]!!
                    } else {
                        multiplyMap[sign] = listOf(number.toInt())
                    }
                }
            }
        }
        println(multiplyMap.filter { it.value.size > 1 }.map { it.value.first() * it.value.last() }.sum())
    }
}

fun String.indexOfAll(occurrence: String): List<Int> {
    var currentIndex = 0
    val allIndexes = mutableListOf<Int>()

    while (currentIndex >= 0) {
        val nextIndex = indexOf(occurrence, currentIndex)
        if (nextIndex > 0) {
            if (!toCharArray()[nextIndex + occurrence.length].isDigit() && !toCharArray()[nextIndex - 1].isDigit()) {
                allIndexes.add(nextIndex)
                currentIndex = nextIndex + 1
            } else {
                currentIndex = nextIndex + occurrence.length
            }
        } else {
            currentIndex = nextIndex
        }
    }
    return allIndexes
}