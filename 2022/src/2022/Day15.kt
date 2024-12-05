package `2022`

import java.io.File
import kotlin.math.abs

fun main() {
    val input = File(ClassLoader.getSystemResource("2022/dummy15.txt").toURI()).readLines()


    //val input = File(ClassLoader.getSystemResource("2022/input15.txt").toURI()).readLines()

    // 5710309 too high
    // 5144286 correct
    Day15.part1(input)
    Day15.part2(input)
}

object Day15 {


    fun part1(input: List<String>) {
        val relevantLine = 10
        val map = input.fold(mutableMapOf<Pair<Int, Int>, String>()) { acc, line ->
            val sensor = line
                .substring(0, line.indexOf(":"))
                .removePrefix("Sensor at x=")
                .split(", y=")
                .map(String::toInt)

            val beacon = line
                .substring(line.lastIndexOf("x") + 2)
                .split(", y=")
                .map(String::toInt)

            acc[Pair(sensor.first(), sensor.last())] = "S"
            acc[Pair(beacon.first(), beacon.last())] = "B"

            val distance = abs(sensor.first() - beacon.first()) + abs(sensor.last() - beacon.last())

            // only continue if the relevant line lies within the possible distance
            val yDiff = relevantLine - sensor.last()
            if (abs(yDiff) <= distance) {
                val xDistance = distance - abs(yDiff)
                (0..xDistance).forEach { diff ->
                    acc.putIfAbsent(Pair(sensor.first() - diff, sensor.last() + yDiff), "#")
                    acc.putIfAbsent(Pair(sensor.first() + diff, sensor.last() + yDiff), "#")
                }
            }

            acc
        }
        /*(-2..22).forEach { y ->
            print("$y ")
            (-10..25).forEach { x ->
                print(map[Pair(x, y)] ?: ".")
            }
            print("\n")
        }*/
        println("Part1: ${map.filter { it.key.second == relevantLine }.count { it.value == "#" }}")
    }

    fun part2(input: List<String>) {
        val max = 20
        val map = input.fold(mutableMapOf<Pair<Int, Int>, String>()) { acc, line ->
            val sensor = line
                .substring(0, line.indexOf(":"))
                .removePrefix("Sensor at x=")
                .split(", y=")
                .map(String::toInt)

            val beacon = line
                .substring(line.lastIndexOf("x") + 2)
                .split(", y=")
                .map(String::toInt)

            acc[Pair(sensor.first(), sensor.last())] = "S"
            acc[Pair(beacon.first(), beacon.last())] = "B"

            val distance = abs(sensor.first() - beacon.first()) + abs(sensor.last() - beacon.last())

            // Only if any x and y value can land within the relevant area

            val maxNegXDist = if (sensor.first() - distance >= 0) distance else sensor.first()
            val maxPosXDist = if (sensor.first() + distance <= max) distance else max - sensor.first()
            val maxNegYDist = if (sensor.last() - distance >= 0) distance else sensor.last()
            val maxPosYDist = if (sensor.last() - distance <= max) distance else max - sensor.last()

            // left top
            (0..maxNegXDist).forEach { xDistance ->
                val yDistance = distance - xDistance
                if (yDistance <= maxNegYDist) {
                    (0..xDistance).forEach { diff ->
                        acc.putIfAbsent(Pair(sensor.first() - diff, sensor.last() - yDistance), "#")
                    }
                }
            }
            // right top
            (0..maxPosXDist).forEach { xDistance ->
                val yDistance = distance - xDistance
                if (yDistance <= maxNegYDist) {
                    (0..xDistance).forEach { diff ->
                        acc.putIfAbsent(Pair(sensor.first() + diff, sensor.last() - yDistance), "#")
                    }
                }
            }
            // right bottom
            (0..maxPosXDist).forEach { xDistance ->
                val yDistance = distance - xDistance
                if (yDistance <= maxPosYDist) {
                    (0..xDistance).forEach { diff ->
                        acc.putIfAbsent(Pair(sensor.first() + diff, sensor.last() + yDistance), "#")
                    }
                }
            }

            // left bottom
            (0..maxNegXDist).forEach { xDistance ->
                val yDistance = distance - xDistance
                if (yDistance <= maxPosYDist) {
                    (0..xDistance).forEach { diff ->
                        acc.putIfAbsent(Pair(sensor.first() - diff, sensor.last() + yDistance), "#")
                    }
                }
            }

            /*(0..distance).forEach { xDistance ->
                val yDistance = distance - xDistance
                (0..xDistance).forEach { diff ->
                    acc.putIfAbsent(Pair(sensor.first() - diff, sensor.last() - yDistance), "#")
                    acc.putIfAbsent(Pair(sensor.first() - diff, sensor.last() + yDistance), "#")
                    acc.putIfAbsent(Pair(sensor.first() + diff, sensor.last() - yDistance), "#")
                    acc.putIfAbsent(Pair(sensor.first() + diff, sensor.last() + yDistance), "#")
                }
            }*/



            acc
        }

        (0..20).forEach { y ->
            //print("$y ")
            (0..20).forEach { x ->
                print(map[Pair(x, y)] ?: ".")
            }
            print("\n")
        }

        println("Part2: ${map.count { it.value == "#" }}")
    }
}
