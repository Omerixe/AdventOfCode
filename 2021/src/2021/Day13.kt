package `2021`

import java.io.File

fun main() {
    val input = File(ClassLoader.getSystemResource("2021/input13.txt").toURI()).readLines()

    val dots = input.map { it.split(",") }.filter { it.size == 2 }.map { it.map(String::toInt) }
    val commands = input.map { it.split(" ") }.filter { it.size == 3 }.map { it[2].split("=") }

    var sheet = (0 until (dots.map { it[1] }.maxOrNull() ?: 0) + 1).map {
        (0 until (dots.map { it[0] }.maxOrNull() ?: 0) + 1).map { "." }.toMutableList()
    }.toMutableList()

    dots.forEach {
        sheet[it[1]][it[0]] = "#"
    }

    commands.forEachIndexed { index, command ->
        when (command[0]) {
            "y" -> {
                val keep = sheet.take(command[1].toInt())
                val fold = sheet.subList(command[1].toInt() + 1, sheet.size).reversed()

                val startIdx = keep.size - fold.size
                val newSheet = keep.mapIndexed { y, line ->
                    if (y >= startIdx) {
                        line.mapIndexed { x, cell ->
                            if (fold[y - startIdx][x] == "#") "#" else cell
                        }.toMutableList()
                    } else line
                }.toMutableList()
                sheet = newSheet
            }

            "x" -> {
                val keep = sheet.map { it.take(command[1].toInt()) }
                val fold = sheet.map { it.subList(command[1].toInt() + 1, it.size).reversed() }

                val startIdx = keep.size - fold.size
                val newSheet = keep.mapIndexed { y, line ->
                    line.mapIndexed { x, cell ->
                        if (x >= startIdx) {
                            if (fold[y][x - startIdx] == "#") "#" else cell
                        } else cell
                    }.toMutableList()
                }.toMutableList()
                sheet = newSheet
            }
        }
        //Part 1
        if (index == 0) {
            println("Part1: ${sheet.map { it.map { if (it == "#") 1 else 0 }.sum() }.sum()}")
        }
    }

    println("Part2:")
    printSheet(sheet)
}

private fun printSheet(sheet: List<List<String>>) {
    sheet.forEach {
        it.forEach { print(it) }
        println()
    }
    println()
}