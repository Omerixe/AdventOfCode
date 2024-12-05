package `2021`

import java.io.File

fun main() {
    val input = File(ClassLoader.getSystemResource("2021/input14.txt").toURI()).readLines()
    val template = input.first()
    //`2022`.`2022`.part1(input, template)

    // The idea in `2022`.`2022`.part1 did not finish for `2022`.part2. I again had to check the replies in the reddit thread to figure out
    // how to exactly group my input in order to avoid having a huge list/string
    val rules = input.map { it.split(" -> ") }.filter { it.size == 2 }.map {
        Pair(it[0], Pair("${it[0][0]}${it[1]}", "${it[1]}${it[0][1]}"))
    }.toMap()

    val polymerMap: MutableMap<String, Long> = mutableMapOf()
    template.windowed(2).forEach { polymerMap[it] = (polymerMap[it] ?: 0) + 1L }
    (0 until 40).forEach {
        val copyMap = polymerMap.toMap()
        copyMap.forEach { polymer ->
            if (polymer.value > 0) {
                val pair = polymer.key
                val rule = rules[pair]!!
                polymerMap[pair] = polymerMap[pair]!! - polymer.value
                polymerMap[rule.first] = (polymerMap[rule.first] ?: 0) + polymer.value
                polymerMap[rule.second] = (polymerMap[rule.second] ?: 0) + polymer.value
            }
        }
        if (it == 9) {
            print("Part1: ")
            printResult(polymerMap, template)
        }
    }
    print("Part2: ")
    printResult(polymerMap, template)
}

private fun printResult(
    polymerMap: MutableMap<String, Long>,
    template: String
) {
    val characterMap = mutableMapOf<Char, Long>()
    polymerMap.forEach {
        val (first, second) = it.key.toList()
        characterMap[first] = (characterMap[first] ?: 0) + it.value
        characterMap[second] = (characterMap[second] ?: 0) + it.value
    }
    val cleanedMap = characterMap.map {
        if (it.key == template.first() || it.key == template.last()) {
            it.value / 2 + 1
        } else it.value / 2
    }
    val resultPt1 = (cleanedMap.maxOrNull() ?: 0L) - (cleanedMap.minOrNull() ?: 0L)
    print("$resultPt1")
    println()
}

private fun part1(input: List<String>, template: String) {
    val rules = input.map { it.split(" -> ") }.filter { it.size == 2 }

    var polymer = template
    (0 until 10).forEach {
        polymer = polymer.windowed(2).map {
            val pair = it
            val insertion = rules.filter { it.first() == pair }.map { it[1] }.first()
            listOf(it[0], insertion)
        }.flatten().joinToString("") + polymer.last()
    }
    val count = polymer.groupingBy { it }.eachCount().values
    println(polymer.windowed(2).groupingBy { it }.eachCount())
    val resultPt1 = (count.maxOrNull() ?: 0) - (count.minOrNull() ?: 0)
    println("Part1: $resultPt1")
}