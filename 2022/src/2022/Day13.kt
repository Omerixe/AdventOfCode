package `2022`

import java.io.File

fun main() {

    // 5895 too high
    // 5579 too high
    val input = File(ClassLoader.getSystemResource("2022/input13.txt").toURI()).readText()

    Day13.part1(input.split("\n\n"))
    Day13.part2(input.split("\n").filter { it.isNotBlank() })
}

object Day13 {

    data class Packet(
        val parent: Packet?,
        val children: MutableList<Packet>?,
        val value: Int?
    ) {
        override fun toString(): String {
            return if (children != null) {
                buildString {
                    append("[")
                    children.forEachIndexed { idx, it ->
                        append(it.toString())
                        if (idx < children.size - 1) append(",")
                    }
                    append("]")
                }

            } else {
                value.toString()
            }
        }
    }

    fun part1(input: List<String>) {
        val result = input.mapIndexed { index, pairs ->
            val pair = pairs.split("\n")
            val pairOne = parsePair(pair[0])
            val pairTwo = parsePair(pair[1])
            //println("== Pair ${ index + 1} ==")
            comparePacket(pairOne, pairTwo)
        }.foldIndexed(0) { index, acc, element -> if (element == Result.RIGHT) acc + index + 1 else acc }
        println("Part1: $result")
    }

    enum class Result { RIGHT, WRONG, EQUAL }

    private fun comparePacket(first: Packet, second: Packet): Result {
        if (first.children != null && second.children != null) { //both are lists
            // both are lists, continue with each of their children
            first.children.forEachIndexed { index, firstChild ->
                if (index >= second.children.size) {
                    //println("Right side ran out of items, so inputs are not in the right order")
                    return Result.WRONG
                }
                val secondChild = second.children[index]
                when (val result = comparePacket(firstChild, secondChild)) {
                    Result.RIGHT -> return result
                    Result.WRONG -> return result
                    Result.EQUAL -> {
                        //Do nothing
                    }
                }
            }
            return if (first.children.size == second.children.size) {
                Result.EQUAL
            } else {
                //println("Left side ran out of items, so inputs are in the right order")
                Result.RIGHT
            }
        } else if (first.value != null && second.value != null) { //both are integers
            //println("Compare ${first.value} vs ${second.value}")
            // terminal state, check if the first value is lower than the second value
            return if (first.value < second.value) {
                //println("Left side is smaller, so inputs are in the right order")
                Result.RIGHT
            } else if (first.value == second.value) {
                Result.EQUAL
            } else {
                //println("Right side is smaller, so inputs are not in the right order")
                Result.WRONG
            }
        } else {
            if (first.value != null) { //first is integer and second is list
                //println("Mixed types; convert left to [${first.value}] and retry comparison")
                return comparePacket(Packet(null, mutableListOf(first), null), second)
            } else { // second is integer and first is list
                //println("Mixed types; convert right to [${second.value}] and retry comparison")
                return comparePacket(first, Packet(null, mutableListOf(second), null))
            }
        }
    }


    private fun parsePair(input: String): Packet {
        val initialPacket = Packet(null, mutableListOf(), null)
        var skipNext = false
        return input
            .drop(1) // drop first [
            .foldIndexed(initialPacket) { index, acc, character ->
                if (skipNext) {
                    skipNext = false
                    acc
                } else {
                    when (character) {
                        '[' -> {
                            val newPacket = Packet(acc, mutableListOf(), null)
                            acc.children!!.add(newPacket)
                            newPacket
                        }

                        ']' -> {
                            acc.parent ?: acc
                        }

                        ',' -> {
                            acc
                        }

                        else -> {
                            if (character.isDigit() && input.drop(1)[index + 1] == '0') {
                                val newPacket = Packet(acc, null, 10)
                                acc.children!!.add(newPacket)
                                skipNext = true
                            } else if (character.isDigit()) {
                                val newPacket = Packet(acc, null, character.digitToInt())
                                acc.children!!.add(newPacket)
                            }
                            acc
                        }
                    }
                }
            }
    }

    fun part2(input: List<String>) {
        val firstDivider = Packet(null, mutableListOf(Packet(null, mutableListOf(Packet(null, null, 2)), null)), null)
        val secondDivider = Packet(null, mutableListOf(Packet(null, mutableListOf(Packet(null, null, 6)), null)), null)
        val result = (input.map { parsePair(it) } + listOf(firstDivider, secondDivider))
            .sortedWith { first, second ->
                when (comparePacket(first, second)) {
                    Result.RIGHT -> -1
                    Result.WRONG -> 1
                    Result.EQUAL -> 0
                }
            }
        val indexFirstDivider = result.indexOfFirst { it == firstDivider } + 1
        val indexSecondDivider = result.indexOfFirst { it == secondDivider } + 1
        println("Part2: ${indexFirstDivider * indexSecondDivider}")
    }
}
