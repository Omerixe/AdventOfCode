package `2024`

import java.io.File

fun main() {
    //val inputs = File(ClassLoader.getSystemResource("2024/dummy04.txt").toURI()).readLines()

    val inputs = File(ClassLoader.getSystemResource("2024/input04.txt").toURI()).readLines()

    Day04.part1(inputs)
    Day04.part2(inputs)
}

object Day04 {
    fun part1(input: List<String>) {
        val result = input.flatMapIndexed() { y, line ->
            line.mapIndexed { x, _ ->
                countEachDirection(input, x, y, "XMAS")
            }
        }.sum()
        println(result)
    }

    fun part2(input: List<String>) {
        val result = input.flatMapIndexed { y, line ->
            line.mapIndexed { x, _ ->
                blub(input, x, y)
            }
        }.filter { it }.size
        println(result)
    }

    fun countEachDirection(input: List<String>, x: Int, y: Int, search: String): Int {
        val searchLength = search.length - 1
        var count = 0
        // north
        if (y - searchLength >= 0) {
            var result = ""
            (y downTo y - searchLength).map { sY ->
                result += input[sY][x]
            }
            if (result == search) {
                count++
            }
        }
        // north east
        if (x + searchLength < input.first().length && y - searchLength >= 0) {
            var result = ""
            for (dist in 0..searchLength) {
                result += input[y - dist][x + dist]
            }
            if (result == search) {

                count++
            }
        }
        // east
        if (x + searchLength < input.first().length) {
            var result = ""
            for (sX in x..x + searchLength) {
                result += input[y][sX]
            }
            if (result == search) {

                count++
            }
        }
        // south east
        if (y + searchLength < input.size && x + searchLength < input.first().length) {
            var result = ""
            for (dist in 0..searchLength) {
                result += input[y + dist][x + dist]
            }
            if (result == search) {

                count++
            }
        }
        // south
        if (y + searchLength < input.size) {
            var result = ""
            for (sY in y..y + searchLength) {
                result += input[sY][x]
            }
            if (result == search) {

                count++
            }
        }
        // south west
        if (y + searchLength < input.size && x - searchLength >= 0) {
            var result = ""
            for (dist in 0..searchLength) {
                result += input[y + dist][x - dist]
            }
            if (result == search) {

                count++
            }
        }
        // west
        if (x - searchLength >= 0) {
            var result = ""
            for (sX in x downTo x - searchLength) {
                result += input[y][sX]
            }
            if (result == search) {

                count++
            }
        }
        // north west
        if (y - searchLength >= 0 && x - searchLength >= 0) {
            var result = ""
            for (dist in 0..searchLength) {
                result += input[y - dist][x - dist]
            }
            if (result == search) {
                count++
            }
        }
        return count
    }

    fun blub(input: List<String>, x: Int, y: Int): Boolean {
        if (y - 1 >= 0 && x - 1 >= 0 && y + 1 < input.size && x + 1 < input.first().length) {
            val middle = input[y][x]
            return if (middle == 'A') {
                val upLeft = input[y - 1][x - 1]
                val upRight = input[y - 1][x + 1]
                val downLeft = input[y + 1][x - 1]
                val downRight = input[y + 1][x + 1]

                val first = "" + upLeft + middle + downRight
                val second = "" + upRight + middle + downLeft

                ((first == "MAS" || first.reversed() == "MAS") && (second == "MAS" || second.reversed() == "MAS"))
            } else {
                false
            }
        } else return false
    }
}
