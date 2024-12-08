package `2024`

import java.io.File

fun main() {
    //val inputs = File(ClassLoader.getSystemResource("2024/dummy08.txt").toURI()).readLines()

    val inputs = File(ClassLoader.getSystemResource("2024/input08.txt").toURI()).readLines()

    Day08.part1(inputs)
    Day08.part2(inputs)
}

object Day08 {
    fun part1(input: List<String>) {
        val width = input.first().length
        val height = input.size
        val result = createAntennaPointMap(input).flatMap {
            it.value.flatMap { first ->
                it.value.mapNotNull { second ->
                    if (first == second) null
                    else {
                        //calculate each antinode
                        val m = first.slope(second)
                        val point1 = first.plus(m)
                        val point2 = second.minus(m)
                        listOf(point1, point2)
                    }
                }.flatten()
            }
        }.toSet().filter { point ->
            point.y in 0 until height && point.x in 0 until width
        }.size
        println(result)
    }

    private fun createAntennaPointMap(input: List<String>) = input.flatMapIndexed { y, line ->
        line.mapIndexedNotNull { x, char ->
            if (char != '.') {
                Pair(char, Pair(x, y))
            } else null
        }
    }.fold(mutableMapOf<Char, List<Pair<Int, Int>>>()) { acc, antenna ->
        if (acc[antenna.first] == null) {
            acc[antenna.first] = listOf(antenna.second)
        } else {
            acc[antenna.first] = acc[antenna.first]!!.plus(listOf(antenna.second))
        }
        acc
    }

    fun part2(input: List<String>) {
        val width = input.first().length
        val height = input.size
        val result = createAntennaPointMap(input).flatMap {
            it.value.flatMap { first ->
                it.value.mapNotNull { second ->
                    if (first == second) null
                    else {
                        //calculate each antinode
                        val m = first.slope(second)

                        // Wow.. bruteforce at its best.. can for sure be optimized! But not today
                        (0..(height)).map { i ->
                            first.plus(m * i)
                        }.plus((0..(height)).map { i ->
                            second.minus(m * i)
                        }
                        )
                    }
                }.flatten()
            }
        }.toSet().filter { point ->
            point.y in 0 until height && point.x in 0 until width
        }.size
        println(result)
    }


}
