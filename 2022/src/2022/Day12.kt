package `2022`

import java.io.File
import java.util.*

fun main() {

    val input = File(ClassLoader.getSystemResource("2022/input12.txt").toURI())
        .readLines()
    // 199 too low
    // 488 too high
    // 484 correct, when removing heuristic


    var start = Node(0, 0, 0)
    var goal = Node(0, 0, 0)
    val nodes = input.flatMapIndexed { y, line ->
        line.mapIndexed { x, cell ->
            when (cell) {
                'S' -> {
                    val sNode = Node(x, y, 'a'.code)
                    start = sNode
                    start
                }

                'E' -> {
                    val gNode = Node(x, y, 'z'.code)
                    goal = gNode
                    goal
                }

                else -> {
                    Node(x, y, cell.code)
                }
            }
        }
    }.toSet()

    val graph = Graph(
        allNodes = nodes,
        startNode = start,
        goalNode = goal,
        maxX = input.first().length - 1,
        maxY = input.size - 1
    )

    Day12.part1(graph)
    Day12.part2(graph)
}

data class Graph(
    val allNodes: Set<Node>,
    val startNode: Node,
    val goalNode: Node,
    val maxX: Int,
    val maxY: Int
) {
    fun cost(first: Node, second: Node): Int {
        return 1
    }

    fun neighbors(current: Node): List<Node> {
        val next = mutableListOf<Node>()
        if (current.x + 1 <= maxX) {
            val find = allNodes.first { it.x == current.x + 1 && it.y == current.y }
            next.add(find)
        }
        if (current.x - 1 >= 0) {
            val find = allNodes.first { it.x == current.x - 1 && it.y == current.y }
            next.add(find)
        }
        if (current.y + 1 <= maxY) {
            val find = allNodes.first { it.x == current.x && it.y == current.y + 1 }
            next.add(find)
        }
        if (current.y - 1 >= 0) {
            val find = allNodes.first { it.x == current.x && it.y == current.y - 1 }
            next.add(find)
        }
        return next
    }

    fun edgePossible(first: Node, second: Node): Boolean {
        val diff = first.value - second.value
        return if (diff >= -1) return true else false
    }
}

data class Node(
    val x: Int,
    val y: Int,
    val value: Int,
)

object Day12 {

    fun part1(graph: Graph) {
        val compareByPriority: Comparator<Pair<Node, Int>> = compareBy { it.second }
        val frontier = PriorityQueue(compareByPriority)
        val cameFrom = mutableMapOf<Node, Node?>()
        val costSoFar = mutableMapOf<Node, Int>()
        frontier.addNode(graph.startNode, 0)
        cameFrom[graph.startNode] = null
        costSoFar[graph.startNode] = 0

        while (frontier.isNotEmpty()) {
            val current = frontier.remove().first

            if (current == graph.goalNode) {
                println("Part 1: ${costSoFar[current]}")
                break
            }

            graph.neighbors(current).filter { graph.edgePossible(current, it) }.forEach { next ->
                val newCost = costSoFar[current]?.plus(graph.cost(current, next)) ?: 0
                if (!costSoFar.contains(next) || newCost < (costSoFar[current] ?: 0)) {
                    costSoFar[next] = newCost
                    val priority = newCost //+ heuristic(next, graph.goalNode)
                    frontier.addNode(next, priority)
                    cameFrom[next] = current
                }
            }
        }
        println()
    }

    private fun heuristic(start: Node, end: Node): Int {
        return end.x - start.x + end.y - start.y
    }

    fun part2(graph: Graph) {
        val result = graph.allNodes.filter { it.value == 'a'.code }.map {
            dijkstra(graph, it)
        }.filter { it > -1 }.min()
        println("Part 2: $result")
    }

    fun dijkstra(graph: Graph, startNode: Node): Int {
        val compareByPriority: Comparator<Pair<Node, Int>> = compareBy { it.second }
        val frontier = PriorityQueue(compareByPriority)
        val cameFrom = mutableMapOf<Node, Node?>()
        val costSoFar = mutableMapOf<Node, Int>()
        frontier.addNode(startNode, 0)
        cameFrom[startNode] = null
        costSoFar[startNode] = 0

        while (frontier.isNotEmpty()) {
            val current = frontier.remove().first

            if (current == graph.goalNode) {
                return costSoFar[current]!!
            }

            graph.neighbors(current).filter { graph.edgePossible(current, it) }.forEach { next ->
                val newCost = costSoFar[current]?.plus(graph.cost(current, next)) ?: 0
                if (!costSoFar.contains(next) || newCost < (costSoFar[current] ?: 0)) {
                    costSoFar[next] = newCost
                    val priority = newCost //+ heuristic(next, graph.goalNode)
                    frontier.addNode(next, priority)
                    cameFrom[next] = current
                }
            }
        }
        return -1
    }

    fun PriorityQueue<Pair<Node, Int>>.addNode(node: Node, priority: Int) {
        add(Pair(node, priority))
    }
}
