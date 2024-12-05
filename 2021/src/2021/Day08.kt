package `2021`

import java.io.File

fun main() {

    val input = File(ClassLoader.getSystemResource("2021/input08.txt").toURI()).readLines()
        .map { it.split(" | ").map { it.split(" ") } }

    val result = input.map { line ->
        line[1].map { it.length }
    }.flatten().filter { it == 2 || it == 4 || it == 3 || it == 7 }.size
    println("Result Pt1: $result")

    val resultPt2 = input.map { line ->
        val charMap = mutableMapOf<String, String>()

        // 1 and 7 share two characters and the one that is left, is a
        line.first()
            .filter { it.length == 2 || it.length == 3 }
            .map { it.split("") }
            .flatten()
            .groupingBy { it }
            .eachCount()
            .forEach {
                if (it.value == 1) charMap["a"] = it.key
            }

        // Count how often each char appears
        val charCount = line.first().map {
            it.split("")
        }.flatten().groupingBy { it }.eachCount()

        // Use some heuristics to map some of the chars as they have unique counts
        // a and b both have 8 characters, but as we already have a, we know that the other is c
        charCount.forEach { entry ->
            when (entry.value) {
                4 -> charMap["e"] = entry.key
                6 -> charMap["b"] = entry.key
                9 -> charMap["f"] = entry.key
                8 -> {
                    if (entry.key != charMap["a"]) {
                        charMap["c"] = entry.key
                    }
                }
            }
        }

        // 4 & 8 share b, c, d, f and we already know b, c, f which means we can find out d
        line.first()
            .filter { it.length == 4 || it.length == 7 }
            .map { it.split("") }
            .flatten()
            .groupingBy { it }
            .eachCount()
            .forEach {
                if (it.value == 2 && it.key != charMap["b"] && it.key != charMap["c"] && it.key != charMap["f"]) charMap["d"] =
                    it.key
            }

        // Now that we know d, we can find out g as both share a count of 6 times
        charCount.forEach { entry ->
            if (entry.value == 7 && entry.key != charMap["d"]) charMap["g"] = entry.key
        }

        // Create the map of the original characters to numbers
        val originalMap = mapOf(
            Pair("abcefg", 0),
            Pair("cf", 1),
            Pair("acdeg", 2),
            Pair("acdfg", 3),
            Pair("bcdf", 4),
            Pair("abdfg", 5),
            Pair("abdefg", 6),
            Pair("acf", 7),
            Pair("abcdefg", 8),
            Pair("abcdfg", 9)
        )
        // Map each second part of a line to the original numbers
        return@map line.last().map inner@{ entry ->
            // Map the entry to the original characters
            val mappedEntry = entry.map { char ->
                charMap.filter { it.value == char.toString() }.keys.first()
            }.map { it.toCharArray().first() }
            // Map the list of original characters to the correct number
            originalMap.forEach {
                if (it.key.length == mappedEntry.size && it.key.toCharArray().toList().containsAll(mappedEntry)) {
                    return@inner it.value
                }
            }
        }.joinToString("").toInt()
    }.sum()
    println("Result Pt2: $resultPt2")

}