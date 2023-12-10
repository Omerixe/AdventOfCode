package `2023`

import java.io.File

fun main() {
    //val inputs = File(ClassLoader.getSystemResource("2023/dummy08.txt").toURI()).readLines()

    val inputs = File(ClassLoader.getSystemResource("2023/input08.txt").toURI()).readLines()
    Day08.part1(inputs)
    Day08.part2(inputs)
}

object Day08 {
    fun part1(inputs: List<String>) {
        val instructions = inputs.first()
        val nodes = inputs.drop(2).fold(mutableMapOf<String, Pair<String, String>>()) { acc, line ->
            val regex = Regex("""([A-Z]{3}) = \(([A-Z]{3}), ([A-Z]{3})\)""").find(line)
            val source = regex?.groups?.get(1)?.value
            val destinationLeft = regex?.groups?.get(2)?.value
            val destinationRight = regex?.groups?.get(3)?.value
            if (source != null && destinationLeft != null && destinationRight != null) {
                acc[source] = Pair(destinationLeft, destinationRight)
            }
            acc
        }
        var currentNode = "AAA"
        var steps = 0
        while (!currentNode.equals("ZZZ")) {
            instructions.forEach { instruction ->
                steps++
                if (instruction == 'L') {
                    currentNode = nodes[currentNode]!!.first
                } else {
                    currentNode = nodes[currentNode]!!.second
                }
                if (currentNode == "ZZZ") return@forEach
            }

        }
        println(steps)
    }

    fun part2(inputs: List<String>) {
        val instructions = inputs.first()
        val nodes = inputs.drop(2).fold(mutableMapOf<String, Pair<String, String>>()) { acc, line ->
            val regex = Regex("""([A-Z1-9]{3}) = \(([A-Z1-9]{3}), ([A-Z1-9]{3})\)""").find(line)
            val source = regex?.groups?.get(1)?.value
            val destinationLeft = regex?.groups?.get(2)?.value
            val destinationRight = regex?.groups?.get(3)?.value
            if (source != null && destinationLeft != null && destinationRight != null) {
                acc[source] = Pair(destinationLeft, destinationRight)
            }
            acc
        }
        val startNodes = nodes.keys.filter { it.endsWith("A") }
        val currentNodes = startNodes.toMutableList()
        val steps = currentNodes.map { node ->
            var currentNode = node
            var steps = 0L
            while (!currentNode.endsWith("Z")) {
                instructions.forEach { instruction ->
                    steps++
                    if (instruction == 'L') {
                        currentNode = nodes[currentNode]!!.first
                    } else {
                        currentNode = nodes[currentNode]!!.second
                    }
                    if (currentNode.endsWith("Z")) {
                        return@forEach
                    }
                }
            }
            steps
        }
        // This only works because the list makes sure, that after reaching the end node
        // the same sequence is followed as if from start - thanks Eric <3
        val lcm = findLCMOfListOfNumbers(steps)
        println(lcm)
    }

    fun findLCM(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
        var result = numbers[0]
        for (i in 1 until numbers.size) {
            result = findLCM(result, numbers[i])
        }
        return result
    }
}
