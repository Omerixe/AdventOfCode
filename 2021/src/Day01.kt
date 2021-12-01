import java.io.File

fun main() {
    val dummyInput = """
        199
        200
        208
        210
        200
        207
        240
        269
        260
        263
    """.trimIndent()

    /*val numbers = dummyInput.split("\n").map(String::toInt)*/

    val numbers = File("resources/input01.txt")
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