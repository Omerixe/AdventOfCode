package `2021`

import java.io.File

fun main() {
    val input = File(ClassLoader.getSystemResource("2021/input15.txt").toURI()).readLines()

    val allRisks = input.map { it.split("").filter { it.isNotEmpty() }.map(String::toInt) }
    val maxXPt1 = allRisks.first().size - 1
    val maxYPt1 = allRisks.size - 1

    val resultPt1 = AStar(Pair(0, 0), Pair(maxXPt1, maxYPt1), allRisks) - allRisks[0][0]
    println("Part1: $resultPt1")

    val allRisksPt2 = (0 until 5).map { y ->
        allRisks.map { line -> line.map { if (it + y > 9) it + y - 9 else it + y } }
    }.flatten()
        .map { line ->
            (0 until 5).map { x ->
                line.map { if (it + x > 9) it + x - 9 else it + x }
            }.flatten()
        }
    val maxXPt2 = allRisksPt2.first().size - 1
    val maxYPt2 = allRisksPt2.size - 1
    val resultPt2 = AStar(Pair(0, 0), Pair(maxXPt2, maxYPt2), allRisksPt2) - allRisksPt2[0][0]
    println("Part1: $resultPt2")
}

// I knew that A* might be the right algorithm to use, but I had no clue anymore how to implement it. In the end
// I implemented it with the help of Wikipedia :P It's still by far not optimal for the second part, but at least it
// finds the solution within an ok amount of time.
private fun AStar(
    start: Pair<Int, Int>,
    end: Pair<Int, Int>,
    allRisks: List<List<Int>>,

    ): Int {
    val maxX = allRisks.first().size - 1
    val maxY = allRisks.size - 1

    val openSet = mutableListOf(start)
    val cameFrom = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
    val gScore = mutableMapOf<Pair<Int, Int>, Int>()
    val fScore = mutableMapOf<Pair<Int, Int>, Int>()
    gScore[start] = 0
    fScore[start] = h(start, end)

    while (openSet.isNotEmpty()) {
        val current = openSet.minByOrNull { fScore[it] ?: Int.MAX_VALUE }!!
        if (current == end) {
            val result = reconstructPath(cameFrom, current).sumOf { allRisks[it.second][it.first] }
            return result
        }
        openSet.remove(current)

        val next = mutableListOf<Pair<Int, Int>>()
        if (current.first + 1 <= maxX) {
            next.add(Pair(current.first + 1, current.second))
        }
        if (current.first - 1 >= 0) {
            next.add(Pair(current.first - 1, current.second))
        }
        if (current.second + 1 <= maxY) {
            next.add(Pair(current.first, current.second + 1))
        }
        if (current.second - 1 >= 0) {
            next.add(Pair(current.first, current.second - 1))
        }
        next.forEach {
            val tent_gScore = (gScore[current] ?: Int.MAX_VALUE) + allRisks[it.second][it.first]
            if (tent_gScore < (gScore[it] ?: Int.MAX_VALUE)) {
                cameFrom[it] = current
                gScore[it] = tent_gScore
                fScore[it] = tent_gScore + h(it, end)
                if (!openSet.contains(it)) openSet.add(it)
            }
        }
    }
    println("Fail")
    return -1
}

fun reconstructPath(cameFrom: Map<Pair<Int, Int>, Pair<Int, Int>>, current: Pair<Int, Int>): List<Pair<Int, Int>> {
    val totalPath = mutableListOf(current)
    var node = current
    while (cameFrom.contains(node)) {
        node = cameFrom[node]!!
        totalPath.add(node)
    }
    return totalPath
}

fun h(start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
    return end.first - start.first + end.second - start.second
}

//I first tried to solve it recursively which worked for the example input but was way too slow for the real input
fun move(start: Pair<Int, Int>, all: List<List<Int>>): Int {
    val maxX = all.first().size - 1
    val maxY = all.size - 1
    val end = maxX to maxY
    if (start == end) {
        return all[start.second][start.first]
    }
    val next = mutableListOf<Pair<Int, Int>>()
    if (start.first + 1 <= maxX) {
        next.add(Pair(start.first + 1, start.second))
    }
    if (start.second + 1 <= maxY) {
        next.add(Pair(start.first, start.second + 1))
    }
    return all[start.second][start.first] + next.minOf { move(it, all) }
}

