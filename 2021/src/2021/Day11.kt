package `2021`

import java.io.File

fun main() {
    val input =
        File(ClassLoader.getSystemResource("2021/input11.txt").toURI()).readLines()
            .map { it.split("").filter { it.isNotEmpty() }.map(String::toInt) }

    var inputPt1 = input.map { it.toMutableList() }.toMutableList()
    var flashCount = 0
    (0 until 10000).forEach { index ->
        inputPt1 = inputPt1.map { line ->
            line.map { it + 1 }.toMutableList()
        }.toMutableList()

        var newFlashes = inputPt1.mapIndexed { y, line ->
            line.mapIndexed { x, cell ->
                if (cell > 9) Pair(x, y) else null
            }.filterNotNull()
        }.flatten().toMutableList()
        val oldFlashes = mutableListOf<Pair<Int, Int>>()
        while (newFlashes.isNotEmpty()) {
            val tempNew = mutableListOf<Pair<Int, Int>>()
            newFlashes.forEach {
                if (index < 100) {
                    flashCount++
                }

                oldFlashes.add(it)
                //tl
                handleAdjacent(Pair(it.first - 1, it.second - 1), inputPt1, oldFlashes, tempNew)
                //tm
                handleAdjacent(Pair(it.first, it.second - 1), inputPt1, oldFlashes, tempNew)
                //tr
                handleAdjacent(Pair(it.first + 1, it.second - 1), inputPt1, oldFlashes, tempNew)
                //lm
                handleAdjacent(Pair(it.first - 1, it.second), inputPt1, oldFlashes, tempNew)
                //rm
                handleAdjacent(Pair(it.first + 1, it.second), inputPt1, oldFlashes, tempNew)
                //bl
                handleAdjacent(Pair(it.first - 1, it.second + 1), inputPt1, oldFlashes, tempNew)
                //bm
                handleAdjacent(Pair(it.first, it.second + 1), inputPt1, oldFlashes, tempNew)
                //br
                handleAdjacent(Pair(it.first + 1, it.second + 1), inputPt1, oldFlashes, tempNew)
            }
            newFlashes = tempNew.distinct().filter { !oldFlashes.contains(it) }.toMutableList()
        }
        inputPt1 = inputPt1.map { line ->
            line.map { if (it > 9) 0 else it }.toMutableList()
        }.toMutableList()
        if (oldFlashes.size == 100) {
            println(flashCount)
            println(index + 1)
            return
        }
    }
}

private fun handleAdjacent(
    newPoint: Pair<Int, Int>,
    inputPt1: MutableList<MutableList<Int>>,
    oldFlashes: MutableList<Pair<Int, Int>>,
    newFlashes: MutableList<Pair<Int, Int>>
) {
    if (newPoint.second >= 0 &&
        newPoint.second < inputPt1.size &&
        newPoint.first >= 0 &&
        newPoint.first < inputPt1.first().size
    ) {
        inputPt1[newPoint.second][newPoint.first] += 1
        if (inputPt1[newPoint.second][newPoint.first] > 9 && !oldFlashes.contains(newPoint)) {
            newFlashes.add(newPoint)
        }
    }
}