object Day05 {

    fun start() {
        val input = readFile(5).split(",").map { it.toInt() }
        calculate(input)
    }

    fun calculate(input: List<Int>): List<Int> {
        return calculate(input, null, null)
    }

    fun calculate(input: List<Int>, verb: Int?, noun: Int?): List<Int> {
        val newList = input.map { it }.toMutableList()
        if (verb != null) {
            newList[1] = verb
        }
        if (noun != null) {
            newList[2] = noun
        }
        var i = 0
        while (i < newList.size) {
            val instruction = newList[i]
            val opcode = instruction % 100
            val modeParam1 = (instruction / 100) % 10
            val modeParam2 = (instruction / 1000) % 10
            val modeParam3 = (instruction / 10000) % 10
            if (opcode == 99) return newList

            when (opcode) {
                99 -> {
                    println("Done 99")
                    return newList
                }

                1 -> {
                    val val1 = newList[i + 1]
                    val val2 = newList[i + 2]
                    val res = newList[i + 3]
                    val firstParam = getParameter(modeParam1, newList, val1)
                    val secondParam = getParameter(modeParam2, newList, val2)
                    if (modeParam3 == 1) {
                        error("write instructions can never be in immediate mode")
                    }
                    newList[res] = firstParam + secondParam
                    i += 4
                }

                2 -> {
                    val val1 = newList[i + 1]
                    val val2 = newList[i + 2]
                    val res = newList[i + 3]
                    val firstParam = getParameter(modeParam1, newList, val1)
                    val secondParam = getParameter(modeParam2, newList, val2)
                    if (modeParam3 == 1) {
                        error("write instructions can never be in immediate mode")
                    }
                    newList[res] = firstParam * secondParam
                    i += 4
                }

                3 -> {
                    val val1 = newList[i + 1]
                    println("Input please:")
                    newList[val1] = readLine()!!.toInt()
                    i += 2
                }

                4 -> {
                    val val1 = newList[i + 1]
                    val firstParam = getParameter(modeParam1, newList, val1)
                    println("Output: ${firstParam}")
                    i += 2
                }

                5 -> {
                    val val1 = newList[i + 1]
                    val val2 = newList[i + 2]
                    val firstParam = getParameter(modeParam1, newList, val1)
                    val secondParam = getParameter(modeParam2, newList, val2)
                    if (firstParam > 0) {
                        i = secondParam
                    } else {
                        i += 3
                    }
                }

                6 -> {
                    val val1 = newList[i + 1]
                    val val2 = newList[i + 2]
                    val firstParam = getParameter(modeParam1, newList, val1)
                    val secondParam = getParameter(modeParam2, newList, val2)
                    if (firstParam == 0) {
                        i = secondParam
                    } else {
                        i += 3
                    }
                }

                7 -> {
                    val val1 = newList[i + 1]
                    val val2 = newList[i + 2]
                    val res = newList[i + 3]
                    val firstParam = getParameter(modeParam1, newList, val1)
                    val secondParam = getParameter(modeParam2, newList, val2)
                    if (modeParam3 == 1) {
                        error("write instructions can never be in immediate mode")
                    }
                    newList[res] = when {
                        firstParam < secondParam -> 1
                        else -> 0
                    }
                    i += 4
                }

                8 -> {
                    val val1 = newList[i + 1]
                    val val2 = newList[i + 2]
                    val res = newList[i + 3]
                    val firstParam = getParameter(modeParam1, newList, val1)
                    val secondParam = getParameter(modeParam2, newList, val2)
                    if (modeParam3 == 1) {
                        error("write instructions can never be in immediate mode")
                    }
                    newList[res] = when (firstParam) {
                        secondParam -> 1
                        else -> 0
                    }
                    i += 4
                }

                else -> {
                    println("Done $opcode")
                    return newList
                }
            }
        }
        println("Done End")
        return listOf()
    }

    fun getParameter(mode: Int, inputList: List<Int>, value: Int): Int {
        return when (mode) {
            0 -> inputList[value]
            1 -> value
            else -> error("wrong parameter")
        }
    }

    fun test1() {
        calculate(listOf(3, 0, 4, 0, 99))
        calculate(listOf(1002, 4, 3, 4, 33))
        calculate(listOf(1101, 100, -1, 4, 0))
    }

    fun test2() {
        /*calculate(listOf(3,9,8,9,10,9,4,9,99,-1,8))
        calculate(listOf(3,9,7,9,10,9,4,9,99,-1,8))
        calculate(listOf(3,3,1108,-1,8,3,4,3,99))
        calculate(listOf(3,3,1107,-1,8,3,4,3,99))
        calculate(listOf(3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9))
        calculate(listOf(3,3,1105,-1,9,1101,0,0,12,4,12,99,1))*/
        calculate(
            listOf(
                3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99
            )
        )
    }
}

fun main(args: Array<String>) {
    Day05.start()
}