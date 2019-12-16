import java.io.InputStream
import kotlin.math.absoluteValue

fun readFile(day:Int): String {
    val inputStream: InputStream = ClassLoader.getSystemResourceAsStream("input$day.txt")
    var stream =  inputStream.bufferedReader().use { it.readText() }
    stream = stream.replace("\r", "")
    stream = stream.replace("\t", " ")
    return stream
}

fun readFile(year: Int, day: Int): String {
    val inputStream: InputStream = ClassLoader.getSystemResourceAsStream("$year/input$day.txt")
    var stream = inputStream.bufferedReader().use { it.readText() }
    stream = stream.replace("\r", "")
    stream = stream.replace("\t", " ")
    return stream
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