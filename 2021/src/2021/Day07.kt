package `2021`

import java.io.File
import java.lang.Math.abs
import kotlin.math.roundToInt


fun main() {

    val input = File(ClassLoader.getSystemResource("2021/input07.txt").toURI()).readLines().first().split(",")
        .map(String::toInt)

    val median = input.median()
    println("Median: $median")

    val resultPt1 = input.map {
        abs(it - median)
    }.sum()
    println("Result Part1: $resultPt1")

    // I'm not exactly sure why we can't just round the average and take that value, it seems it could either be the
    // upper or lower rounded value of the average, so I just try both and take the minimal output
    val average = input.average()
    val upperAvg = average.roundToInt()
    val lowerAvg = average.toInt()

    println("Average: $average")

    val avgList = if (upperAvg == lowerAvg) listOf(upperAvg) else listOf(upperAvg, lowerAvg)
    val resultPt2 = avgList.map { avg ->
        input.map {
            val diff = abs(it - avg)
            (1 until diff + 1).sum()
        }.sum()
    }.minOrNull()

    println("Result Part2: $resultPt2")
}

fun List<Int>.median(): Int {
    val array = this.toTypedArray()
    array.sort()
    return if (array.size % 2 == 0) (array.get(array.size / 2) + array.get(array.size / 2 - 1)) / 2 else array.get(
        array.size / 2
    )
}