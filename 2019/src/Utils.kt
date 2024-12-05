import java.io.File
import kotlin.math.absoluteValue

fun readFile(day: Int): String {
    return File("resources/input$day.txt")
        .readLines()
        .joinToString(separator = "\n")
}

//fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

fun gcd(a: Int, b: Int): Int {
    return gcd(a.toLong(), b.toLong()).toInt()
}

fun gcd(a: Long, b: Long): Long {
    if (a == 0L) return b.absoluteValue
    if (b == 0L) return a.absoluteValue

    var newA = a
    var newB = b

    do {
        val h = newA % newB
        newA = newB
        newB = h
    } while (newB != 0L)

    return newA.absoluteValue
}

fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b

fun distanceBetweenTwoPoints(x1: Int, y1: Int, x2: Int, y2: Int): Double {
    return Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)).toDouble())
}