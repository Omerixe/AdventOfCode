package `2023`

import java.io.File

fun main() {
//   val inputs = File(ClassLoader.getSystemResource("2023/dummy01.txt").toURI()).readLines()

    val inputs = File(ClassLoader.getSystemResource("2023/input01.txt").toURI()).readLines()

    Day01.part1(inputs)
    Day01.part2(inputs)
}

object Day01 {
    fun part1(input: List<String>) {
        val result = input.map { line ->
            val digits = line.mapNotNull { it.digitToIntOrNull() }
            (digits.first() * 10) + digits.last()
        }.sum()
        println(result)
    }

    fun part2(input: List<String>) {
        //54957 - too low
        //54984 - wrong
        //54987 - wrong
        //54992 - wrong
        //54981 - wrong
        //54980 - finally

        val result = input.map { line ->
            val first = findFirstNumber(line)
            val last = findLastNumber(line)
            Integer.valueOf("$first$last")
        }.sum()
        println(result)
    }

    fun findFirstNumber(input: String): Int {
        val numbers = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        )

        val firstDigit = input.first { it.isDigit() }
        val firstDigitIndex = input.indexOf(firstDigit)
        if (firstDigitIndex == 0) return Integer.valueOf(firstDigit.toString())

        val allCharNumbers =
            numbers.keys.map { Pair(it, input.indexOf(it)) }.filter { it.second >= 0 }.sortedByDescending { it.second }
        val firstCharNumber = if (allCharNumbers.isNotEmpty()) allCharNumbers.last() else Pair("", Integer.MAX_VALUE)
        return if (firstDigitIndex < firstCharNumber.second) {
            Integer.valueOf(firstDigit.toString())
        } else {
            numbers[firstCharNumber.first]!!
        }
    }

    fun findLastNumber(input: String): Int {
        val numbers = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        )

        val lastDigit = input.last { it.isDigit() }
        val lastDigitIndex = input.lastIndexOf(lastDigit)
        if (lastDigitIndex == input.length - 1) { //zk124jcgnc3three1 - 13
            return Integer.valueOf(lastDigit.toString())
        }

        val allCharNumbers = numbers.keys.map { Pair(it, input.lastIndexOf(it)) }.filter { it.second >= 0 }
            .sortedByDescending { it.second }
        val lastCharNumber = if (allCharNumbers.isNotEmpty()) allCharNumbers.first() else Pair("", Integer.MIN_VALUE)

        return if (lastDigitIndex > lastCharNumber.second) {
            Integer.valueOf(lastDigit.toString())
        } else {
            numbers[lastCharNumber.first]!!
        }
    }
}
