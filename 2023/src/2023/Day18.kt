package `2023`

import java.io.File
import kotlin.math.absoluteValue

fun main() {

    val inputs = File(ClassLoader.getSystemResource("2023/dummy18.txt").toURI()).readLines()
    //val inputs = File(ClassLoader.getSystemResource("2023/input18.txt").toURI()).readLines()
    Day18.part1(inputs)
    Day18.part2(inputs)
}

object Day18 {


    fun part1(inputs: List<String>) {
        val cmdList = inputs.map { it.split(" ").take(2) }
        var currentPosition = Pair(0, 0)
        val edgeList = mutableListOf<Pair<Int, Int>>()
        val digList = cmdList.flatMap { cmd ->
            edgeList.add(currentPosition)
            val direction = cmd.first()
            val distance = cmd.last().toInt()
            when (direction) {
                "R" -> {
                    val list = (1..distance).map {
                        Pair(currentPosition.first, currentPosition.second + it)
                    }
                    currentPosition = Pair(currentPosition.first, currentPosition.second + distance)
                    list
                }

                "L" -> {
                    val list = (1..distance).map {
                        Pair(currentPosition.first, currentPosition.second - it)
                    }
                    currentPosition = Pair(currentPosition.first, currentPosition.second - distance)
                    list
                }

                "U" -> {
                    val list = (1..distance).map {
                        Pair(currentPosition.first - it, currentPosition.second)
                    }
                    currentPosition = Pair(currentPosition.first - distance, currentPosition.second)
                    list
                }

                "D" -> {
                    val list = (1..distance).map {
                        Pair(currentPosition.first + it, currentPosition.second)
                    }
                    currentPosition = Pair(currentPosition.first + distance, currentPosition.second)
                    list
                }

                else -> emptyList()
            }
        }

        val newDigList = digList.sortedWith { lh, rh ->
            if (lh.first == rh.first) {
                if (lh.second > rh.second) 1 else -1
            } else if (lh.first > rh.first) 1
            else -1
        }
        // does not work as the border is sometimes counted as inside
        edgeList.add(edgeList.first())
        val res = edgeList.windowed(2).fold(0L) { acc, coords ->
            println(acc)
            val point1 = coords.first()
            val point2 = coords.last()
            acc + (point1.second * point2.first) - (point1.first * point2.second)
            //acc + (point1.second - point2.second) * (point1.first + point2.first)
        }.absoluteValue / 2 + newDigList.size

        /*
        #######
        #.....#
        ###...#
         */

        println(res)
        val maxDepth = digList.maxOf { it.first }
        val minDepth = digList.minOf { it.first }
        val maxWidth = digList.maxOf { it.second }
        val minWidth = digList.minOf { it.second }

        (minDepth..maxDepth).forEach { y ->
            (minWidth..maxWidth).forEach { x ->
                if (digList.contains(Pair(maxDepth - y, x))) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
    }


    fun part2(inputs: List<String>) {

    }


}

