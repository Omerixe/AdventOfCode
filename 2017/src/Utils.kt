import java.io.File

fun readFile(day: Int): String {
    return File("resources/input$day.txt")
        .readLines()
        .joinToString(separator = "\n")
}