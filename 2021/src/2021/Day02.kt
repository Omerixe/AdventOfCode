package `2021`

import java.io.File

fun main() {

    val input = File(ClassLoader.getSystemResource("2021/input02.txt").toURI())
        .readLines()

    var depthPt1 = 0
    var horPosPt1 = 0
    input.forEach {
        val parts = it.split(" ")
        val amount = parts.last().toInt()
        when (parts.first()) {
            "forward" -> horPosPt1 += amount
            "up" -> depthPt1 -= amount
            "down" -> depthPt1 += amount
        }
    }
    println("Depth: $depthPt1, horizontal Position: $horPosPt1, Product: ${depthPt1 * horPosPt1}")

    var depthPt2 = 0
    var horPosPt2 = 0
    var aim = 0

    input.forEach {
        val parts = it.split(" ")
        val amount = parts.last().toInt()
        when (parts.first()) {
            "forward" -> {
                horPosPt2 += amount
                depthPt2 += aim * amount
            }

            "up" -> aim -= amount
            "down" -> aim += amount
        }
    }
    println("Depth: $depthPt2, horizontal Position: $horPosPt2, Product: ${depthPt2 * horPosPt2}")
}