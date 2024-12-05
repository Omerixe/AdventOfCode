object Day14 {
    val originalInput = readFile(14).first()

    fun start() {
        val inputStrings = listOf(0..127).flatten().map { originalInput + "-" + it }
        val inputLengths = inputStrings.map { it.toCharArray().map { it.toInt() } }
        val inputGrid = mutableListOf<CharArray>()
        var free = 0
        for (inputLentgth in inputLengths) {
            val denseHash = Day10.makeKnotHash(inputLentgth)
            var binString = ""
            for (hash in denseHash) {
                val temp = hash.toString(2)
                if (temp.length < 8) {
                    val rem = 8 - temp.length
                    var tempIt = temp
                    for (i in 1..rem) {
                        tempIt = "0" + tempIt
                    }
                    binString += tempIt
                } else {
                    binString += temp
                }
            }
            inputGrid.add(binString.toCharArray())
            val used = binString.split("1").count() - 1
            free += used
        }
        println(free)

        createGrid(inputGrid)
    }

    fun createGrid(inputGrid: MutableList<CharArray>) {
        val gridmap: MutableMap<Pair<Int, Int>, Node14> = mutableMapOf()
        for ((n, row) in inputGrid.withIndex()) {
            for ((m, column) in inputGrid.withIndex()) {
                val index = Pair(n, m)
                val newNode = Node14(row[m].toString())
                if (row[m] == '0') {
                    newNode.visited = true
                }
                gridmap.put(index, newNode)
            }
        }
        for ((n, row) in inputGrid.withIndex()) {
            for ((m, column) in inputGrid.withIndex()) {
                val index = Pair(n, m)
                val node = gridmap.get(index)
                if (node != null) {
                    //up
                    var blub = gridmap.get(Pair(n + 1, m))
                    if (blub != null) {
                        if (!node.edges.contains(blub)) {
                            node.edges.add(blub)
                            blub.edges.add(node)
                        }
                    }
                    //down
                    blub = gridmap.get(Pair(n - 1, m))
                    if (blub != null) {
                        if (!node.edges.contains(blub)) {
                            node.edges.add(blub)
                            blub.edges.add(node)
                        }
                    }
                    //right
                    blub = gridmap.get(Pair(n, m + 1))
                    if (blub != null) {
                        if (!node.edges.contains(blub)) {
                            node.edges.add(blub)
                            blub.edges.add(node)
                        }
                    }
                    //left
                    blub = gridmap.get(Pair(n, m - 1))
                    if (blub != null) {
                        if (!node.edges.contains(blub)) {
                            node.edges.add(blub)
                            blub.edges.add(node)
                        }
                    }
                }
            }
        }
        var count = 0

        while (gridmap.filter { it.value.visited == false }.size > 0) {
            val nextIdx = gridmap.filter { it.value.visited == false }.keys.first()
            val nextStart = gridmap.get(nextIdx)
            if (nextStart != null) {
                count++
                nextStart.visit()
            }
        }
        println(count)
    }
}

fun main(args: Array<String>) {
    Day14.start()
}

class Node14(var value: String, var visited: Boolean = false) {
    var edges: MutableList<Node14> = mutableListOf()

    fun visit() {
        visited = true
        for (edge in edges) {
            if (!edge.visited && edge.value.equals(value)) {
                edge.visit()
            }
        }
    }
}