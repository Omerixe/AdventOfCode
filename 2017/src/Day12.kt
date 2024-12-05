object Day12 {

    fun start() {
        val input = readFile(12)
        val inputSplitted = input.split("\n").map { it.split(" <-> ") }
        println(inputSplitted.indexOfFirst { it.get(0) == "" })
        val nodes = inputSplitted.associateBy({ it.get(0).toInt() }, { Node2(it.get(0).toInt()) })
        for (inputNode in inputSplitted) {
            val sourceNode = nodes.get(inputNode.get(0).toInt())
            if (sourceNode != null) {
                val destinationNodes = inputNode.get(1).split(", ")
                for (destination in destinationNodes) {
                    val destinationNode = nodes.get(destination.toInt())
                    if (destinationNode != null) {
                        destinationNode.edges.add(sourceNode)
                        sourceNode.edges.add(destinationNode)
                    }
                }
            }
        }
        nodes.get(0)?.visit()
        println(nodes.filter { it.value.visited == true }.size)
        var count = 1

        while (nodes.filter { it.value.visited == false }.size > 0) {
            val nextIdx = nodes.filter { it.value.visited == false }.keys.first()
            val nextStart = nodes.get(nextIdx)
            if (nextStart != null) {
                count++
                nextStart.visit()
            }
        }
        println(count)
    }


}

fun main(args: Array<String>) {
    Day12.start()
}

class Node2(var id: Int, var visited: Boolean = false) {
    var edges: MutableList<Node2> = mutableListOf()

    fun visit() {
        visited = true
        for (edge in edges) {
            if (!edge.visited) {
                edge.visit()
            }
        }
    }
}