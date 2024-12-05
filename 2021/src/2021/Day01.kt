package `2021`

import java.io.File

fun main() {

    val numbers = File(ClassLoader.getSystemResource("2021/input01.txt").toURI())
        .readLines()
        .map(String::toInt)

    val amountPt1 = numbers
        .zipWithNext()
        .map { pair ->
            pair.first < pair.second
        }
        .filter { it }
        .size

    println(amountPt1)

    val amountPt2 = numbers
        .windowed(3)
        .map { window -> window[0] + window[1] + window[2] }
        .zipWithNext()
        .map { pair ->
            pair.first < pair.second
        }
        .filter { it }
        .size
    println(amountPt2)

}