object Day7 {

    fun start() {
        //Part 1
        val input = readFile(7)
        val nodes = getNodes(input)
        val parent: Node = nodes.filter { it.value.parent == null }.entries.first().value
        println(parent.name)
        //Part 2
        val problemNode = parent.findUnbalancedProgram()
        println(problemNode?.name)
        if (problemNode != null) {
            val (childNode, valueCorr) = getUnbalancedChild(problemNode)
            println(calculateCorrectValueForProblemChild(childNode, valueCorr))
        }
    }

    fun getValueNode(regex: Regex, parts: List<String>, nodes: MutableMap<String, Node>): Node {
        val groups = regex.matchEntire(parts[0])?.groups
        val name = groups?.get(1)?.value
        val value = groups?.get(2)?.value?.toInt()
        var parentNode: Node? = null
        if (name != null && value != null) {
            if (nodes.containsKey(name)) {
                parentNode = nodes.get(name)
            } else {
                parentNode = Node(value, name)
            }
        }
        return parentNode!!
    }

    fun getNodes(input: String): MutableMap<String, Node> {
        val inputList = input.split("\n")

        val nodes: MutableMap<String, Node> = mutableMapOf()
        val parentChildren: MutableMap<String, String> = mutableMapOf()

        //First we only try to find out the values for all Nodes and ignore the children
        inputList.forEach {
            val regex = Regex("([a-z]*) \\(([0-9]+)\\)")
            val parts = it.split(" -> ")
            val parent = getValueNode(regex, parts, nodes)
            nodes.put(parent.name, parent)
            if (parts.size == 2) {
                parentChildren.put(parent.name, parts[1])
            }
        }
        //Second add all the Children
        for ((key, value) in parentChildren.entries) {
            val children = value.split(", ")
            for (child in children) {
                val childNode = nodes.get(child)
                if (childNode != null) {
                    val parent = nodes.get(key)
                    if (parent != null) {
                        parent.children.add(childNode)
                        childNode.parent = parent
                    }
                }
            }
        }
        return nodes
    }

    fun getUnbalancedChild(problemNode: Node): Pair<Node, Int> {
        var valueCorr = 0
        var valueWrong = 0
        for (child in problemNode.children) {
            if (valueCorr == 0) {
                valueCorr = child.newValue
            } else if (valueCorr == child.newValue) {
                continue
            } else if (valueWrong == 0) {
                valueWrong = child.newValue
            } else {
                valueWrong = valueCorr
                valueCorr = child.newValue
            }
        }
        return Pair(problemNode.children.find { it.newValue == valueWrong }!!, valueCorr)
    }

    fun calculateCorrectValueForProblemChild(problemChild: Node, correctValue: Int): Int {
        var childValue = 0
        for (child in problemChild.children) {
            childValue += child.newValue
        }
        return (correctValue - childValue)
    }
}

fun main(args: Array<String>) {
    Day7.start()
}

class Node(var value: Int, var name: String) {
    var parent: Node? = null
    var children: MutableList<Node> = mutableListOf()
    var newValue = 0

    fun findUnbalancedProgram(): Node? {
        if (children.size == 0) {
            newValue = value
            return null
        } else {
            var sum = value
            var refVal = 0
            for (child in children) {
                val unbalancedProgram = child.findUnbalancedProgram()
                if (unbalancedProgram != null) {
                    return unbalancedProgram
                }
                if (refVal == 0) {
                    refVal = child.newValue
                } else if (refVal != child.newValue) {
                    return this
                }
                sum += child.newValue
            }
            newValue = sum
            return null
        }
    }
}


