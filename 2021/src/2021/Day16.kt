package `2021`

import java.io.File

fun main() {
    val input = File(ClassLoader.getSystemResource("2021/input16.txt").toURI()).readLines().first()

    val binary = input.map {
        when (it) {
            '0' -> "0000"
            '1' -> "0001"
            '2' -> "0010"
            '3' -> "0011"
            '4' -> "0100"
            '5' -> "0101"
            '6' -> "0110"
            '7' -> "0111"
            '8' -> "1000"
            '9' -> "1001"
            'A' -> "1010"
            'B' -> "1011"
            'C' -> "1100"
            'D' -> "1101"
            'E' -> "1110"
            'F' -> "1111"
            else -> ""
        }
    }.joinToString("")

    val (_, sum, result) = readPacket(binary, 0, 0)
    println("Part1: $sum")
    println("Part2: $result")
}

private fun readPacket(binary: String, startIndex: Int, startSum: Int): Triple<Int, Int, Long> {
    var currentIndex = startIndex
    var sum = startSum
    var result = 0L
    val version = Integer.parseInt(binary.substring(currentIndex, currentIndex + 3), 2)
    currentIndex += 3
    sum += version
    val typeId = Integer.parseInt(binary.substring(currentIndex, currentIndex + 3), 2)
    currentIndex += 3

    when (typeId) {
        0 -> {
            val (newIdx, newSum, allResults) = subPackets(binary, currentIndex, sum)
            currentIndex = newIdx
            sum = newSum
            result = allResults.sum()
        }

        1 -> {
            val (newIdx, newSum, allResults) = subPackets(binary, currentIndex, sum)
            currentIndex = newIdx
            sum = newSum
            result = allResults.fold(1) { acc, new -> acc * new }
        }

        2 -> {
            val (newIdx, newSum, allResults) = subPackets(binary, currentIndex, sum)
            currentIndex = newIdx
            sum = newSum
            result = allResults.minOrNull()!!
        }

        3 -> {
            val (newIdx, newSum, allResults) = subPackets(binary, currentIndex, sum)
            currentIndex = newIdx
            sum = newSum
            result = allResults.maxOrNull()!!
        }

        4 -> {
            var resultLiteral = ""
            while (binary[currentIndex] == '1') {
                resultLiteral += binary.substring(currentIndex + 1, currentIndex + 5)
                currentIndex += 5
            }
            resultLiteral += binary.substring(currentIndex + 1, currentIndex + 5)
            currentIndex += 5
            result = resultLiteral.toLong(2)
        }

        5 -> {
            val (newIdx, newSum, allResults) = subPackets(binary, currentIndex, sum)
            currentIndex = newIdx
            sum = newSum
            result = if (allResults.first() > allResults.last()) 1 else 0
        }

        6 -> {
            val (newIdx, newSum, allResults) = subPackets(binary, currentIndex, sum)
            currentIndex = newIdx
            sum = newSum
            result = if (allResults.first() < allResults.last()) 1 else 0
        }

        7 -> {
            val (newIdx, newSum, allResults) = subPackets(binary, currentIndex, sum)
            currentIndex = newIdx
            sum = newSum
            result = if (allResults.first() == allResults.last()) 1 else 0
        }

        else -> {
            val typeLengthId = Integer.parseInt(binary.substring(currentIndex, currentIndex + 1), 2)
            currentIndex += 1
            if (typeLengthId == 0) {
                val length = Integer.parseInt(binary.substring(currentIndex, currentIndex + 15), 2)
                currentIndex += 15
                val tempIndex = currentIndex
                while (currentIndex < tempIndex + length) {
                    val (newIdx, newSum) = readPacket(binary, currentIndex, sum)
                    currentIndex = newIdx
                    sum = newSum
                }
            } else {
                val amount = Integer.parseInt(binary.substring(currentIndex, currentIndex + 11), 2)
                currentIndex += 11
                (0 until amount).forEach {
                    val (newIdx, newSum) = readPacket(binary, currentIndex, sum)
                    currentIndex = newIdx
                    sum = newSum
                }
            }
        }
    }
    return Triple(currentIndex, sum, result)
}

fun subPackets(binary: String, startIndex: Int, startSum: Int): Triple<Int, Int, List<Long>> {
    var currentIndex = startIndex
    var sum = startSum
    val allResults = mutableListOf<Long>()

    val typeLengthId = Integer.parseInt(binary.substring(currentIndex, currentIndex + 1), 2)
    currentIndex += 1

    if (typeLengthId == 0) {
        val length = Integer.parseInt(binary.substring(currentIndex, currentIndex + 15), 2)
        currentIndex += 15
        val tempIndex = currentIndex
        println(length)
        while (currentIndex < tempIndex + length) {
            val (newIdx, newSum, result) = readPacket(binary, currentIndex, sum)
            currentIndex = newIdx
            sum = newSum
            allResults.add(result)
        }
    } else {
        val amount = Integer.parseInt(binary.substring(currentIndex, currentIndex + 11), 2)
        currentIndex += 11
        println(amount)
        (0 until amount).forEach {
            val (newIdx, newSum, result) = readPacket(binary, currentIndex, sum)
            currentIndex = newIdx
            sum = newSum
            allResults.add(result)
        }
    }
    return Triple(currentIndex, sum, allResults)
}