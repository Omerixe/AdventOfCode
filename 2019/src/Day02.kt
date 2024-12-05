object Day02 {
    fun start() {
        val input = readFile(2)
        println(part1(input))
        println(part2(input))
    }

    fun part1(input: String): Int {
        val inputSeq = input.split(",").map { it.toInt() }.toMutableList()
        val outputSeq = calculate(inputSeq, 12, 2)
        return outputSeq[0]
    }

    fun calculate(input: List<Int>, verb: Int, noun: Int): List<Int> {
        val newList = input.map { it }.toMutableList()
        newList[1] = verb
        newList[2] = noun
        for (i in 0 until newList.size step 4) {
            val opcode = newList[i]
            if (opcode == 99) return newList
            val val1 = newList[i + 1]
            val val2 = newList[i + 2]
            val res = newList[i + 3]
            newList[res] = when (opcode) {
                1 -> newList[val1] + newList[val2]
                2 -> newList[val1] * newList[val2]
                else -> return newList
            }
        }
        return listOf()
    }

    fun part2(input: String): Int {
        val inputSeq = input.split(",").map { it.toInt() }
        for (noun in 0..99) {
            for (verb in 0..99) {
                val result = calculate(inputSeq, noun, verb)
                if (result[0] == 19690720) {
                    println("noun $noun, verb $verb")
                    return 100 * noun + verb
                }
            }
        }
        return 0
    }

    fun test1() {
        if (part1("1,0,0,0,99") == 2) println("Test1 done")
        if (part1("2,3,0,3,99") == 2) println("Test2 done")
        if (part1("2,4,4,5,99,0") == 2) println("Test3 done")
        if (part1("1,1,1,4,99,5,6,0,99") == 30) println("Test4 done")
    }

}

fun main(args: Array<String>) {
    Day02.start()
}