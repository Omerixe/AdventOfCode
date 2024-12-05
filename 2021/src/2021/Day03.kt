package `2021`

import java.io.File

fun main() {

    val input = File(ClassLoader.getSystemResource("2021/input03.txt").toURI())
        .readLines()

    val numbers = input.map(String::toCharArray)
    part1(numbers)
    part2(numbers)
}

private fun part1(numbers: List<CharArray>) {
    val gamma = (0 until numbers.first().size) //List of indices
        .map { index -> numbers.map { binary -> binary[index] } } //List of numbers for each position
        .map { positionList ->
            if (positionList.count { it == '1' } >= positionList.count { it == '0' }) '1' else '0'
        }
        .joinToString("")//Find stronger number

    val epsilon = gamma.map { if (it == '1') '0' else '1' }.joinToString("")
    val result = gamma.toInt(2) * epsilon.toInt(2)

    println("Result Pt1: $result")
}

private fun part2(numbers: List<CharArray>) {
    var oxygenList = numbers
    numbers.first().forEachIndexed { index, _ ->
        val group = oxygenList.map { it[index] }.groupingBy { it }.eachCount()
        val one = group.get('1')!! >= group.get('0')!!
        if (one) {
            oxygenList = oxygenList.filter { it[index] == '1' }
        } else {
            oxygenList = oxygenList.filter { it[index] == '0' }
        }
    }
    val oxygen = Integer.parseInt(oxygenList.first().joinToString(""), 2)
    println("Oxygen: ${oxygen}")

    var co2ScrubberList = numbers
    numbers.first().forEachIndexed { index, _ ->
        if (co2ScrubberList.size != 1) {
            val group = co2ScrubberList.map { it[index] }.groupingBy { it }.eachCount()
            val one = (group.get('1') ?: 0) >= (group.get('0') ?: 0)
            if (one) {
                co2ScrubberList = co2ScrubberList.filter { it[index] == '0' }
            } else {
                co2ScrubberList = co2ScrubberList.filter { it[index] == '1' }
            }
        }
    }
    val co2Scrubber = Integer.parseInt(co2ScrubberList.first().joinToString(""), 2)
    println("CO2 Scrubber: ${co2Scrubber}")
    println("Product: ${oxygen * co2Scrubber}")
}