object Day06 {
    fun start() {
        val input = readFile(6).split("\n")
        println(part1(input))
        println(part2(input))
    }

    fun part1(input: List<String>): Int {
        val nodeList = createTree(input)
        return nodeList["COM"]?.orbits()!!
    }

    fun part2(input: List<String>): Int {
        val nodeList = createTree(input)

        val you: Tree = nodeList.getValue("YOU")
        val youToCom = you.wayToCom()

        val san: Tree = nodeList.getValue("SAN")
        val sanToCom = san.wayToCom()

        var i = 0
        while (youToCom[i] == sanToCom[i]) {
            i++
        }
        //without intersecting node and without own orbit
        val list1 = youToCom.subList(i, youToCom.size - 2)
        //with intersection node and with own orbit
        val list2 = sanToCom.subList(i - 1, sanToCom.size - 1)
        return list1.size + list2.size
    }

    fun createTree(input: List<String>): Map<String, Tree> {
        val nodeList = mutableMapOf<String, Tree>()
        for (orbit in input.map { it.split(")") }) {
            val from = nodeList.getOrPut(orbit[0], { Tree(orbit[0]) })
            val to = nodeList.getOrPut(orbit[1], { Tree(orbit[1]) })
            to.parent = from
            from.treeList.add(to)
        }
        return nodeList
    }

    fun test1() {
        val input = "COM)B\n" +
                "B)C\n" +
                "C)D\n" +
                "D)E\n" +
                "E)F\n" +
                "B)G\n" +
                "G)H\n" +
                "D)I\n" +
                "E)J\n" +
                "J)K\n" +
                "K)L"

        println(part1(input.split("\n")))
    }

    fun test2() {
        val input = "COM)B\n" +
                "B)C\n" +
                "C)D\n" +
                "D)E\n" +
                "E)F\n" +
                "B)G\n" +
                "G)H\n" +
                "D)I\n" +
                "E)J\n" +
                "J)K\n" +
                "K)L\n" +
                "K)YOU\n" +
                "I)SAN"

        println(part2(input.split("\n")))
    }
}

class Tree(val value: String, var treeList: MutableList<Tree> = mutableListOf(), var parent: Tree? = null) {
    fun orbits(): Int {
        return orbits(0)
    }

    private fun orbits(count: Int): Int {
        val returnValue = count + treeList.map { it.orbits(count + 1) }.sum()
        return returnValue
    }

    fun wayToCom(): MutableList<Tree> {
        if (value == "COM") {
            return mutableListOf(this)
        } else {
            return parent?.wayToCom().apply { this!!.add(this@Tree) }!!
        }
    }
}

fun main(args: Array<String>) {
    Day06.start()
}