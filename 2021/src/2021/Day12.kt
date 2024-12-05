package `2021`

import java.io.File

fun main() {
    val input = File(ClassLoader.getSystemResource("2021/input12.txt").toURI()).readLines()

    val allNodes = input.map { it.split("-") }.map {
        if (it[0] == "start") {
            listOf(Pair(it[0], it[1]))
        } else if (it[1] == "start") {
            listOf(Pair(it[1], it[0]))
        } else if (it[0] == "end") {
            listOf(Pair(it[1], it[0]))
        } else if (it[1] == "end") {
            listOf(Pair(it[0], it[1]))
        } else {
            listOf(Pair(it[0], it[1]), Pair(it[1], it[0]))
        }
    }.flatten()

    println(findPathPt1("start", listOf(), allNodes))
    println(findPathPt2("start", listOf(), null, allNodes))
}

val smallCaves = 'a'..'z'
fun findPathPt1(startNode: String, visitedNodes: List<String>, allNodes: List<Pair<String, String>>): Int {
    if (startNode == "end") return 1
    val nextPaths = allNodes.filter { it.first == startNode && !visitedNodes.contains(it.second) }
    return nextPaths.sumOf {
        val newList = visitedNodes.toMutableList()
        newList.add(it.second)

        findPathPt1(
            it.second,
            if (smallCaves.contains(it.second.toCharArray().first())) newList.toList() else visitedNodes,
            allNodes
        )
    }
}

fun findPathPt2(
    startNode: String,
    visitedNodes: List<String>,
    doubleSmall: String?,
    allNodes: List<Pair<String, String>>
): Int {
    if (startNode == "end") return 1
    val nextPaths =
        allNodes.filter { it.first == startNode && (!visitedNodes.contains(it.second) || doubleSmall == null) }
    return nextPaths.sumOf {
        val newList = visitedNodes.toMutableList()
        newList.add(it.second)
        var newDoubleSmall = doubleSmall
        val returnList = if (smallCaves.contains(it.second.toCharArray().first())) {
            if (visitedNodes.contains(it.second)) newDoubleSmall = it.second
            newList.toList()
        } else visitedNodes
        findPathPt2(
            it.second,
            returnList,
            newDoubleSmall,
            allNodes
        )
    }
}