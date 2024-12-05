class IntcodeComputer(
    input: List<Int>,
    verb: Int? = null,
    noun: Int? = null,
    val manualMode: Boolean = false,
    val returnOnOutput: Boolean = false
) {
    private val stateList = input.toMutableList()
    private val inputList = mutableListOf<Int>()
    private val outList = mutableListOf<Int>()
    private var inputIdx = 0
    private var instPointer = 0
    var isDone = false

    init {
        if (verb != null) {
            stateList[1] = verb
        }
        if (noun != null) {
            stateList[2] = noun
        }
    }

    fun calculate(inputs: List<Int> = listOf()): Pair<List<Int>, List<Int>> {
        if (isDone) {
            return Pair(stateList, listOf())
        }
        inputList.addAll(inputs)
        while (instPointer < stateList.size) {
            val instruction = stateList[instPointer]
            val opcode = instruction % 100
            val modeParam1 = (instruction / 100) % 10
            val modeParam2 = (instruction / 1000) % 10
            val modeParam3 = (instruction / 10000) % 10

            when (opcode) {
                99 -> {
                    println("Done 99")
                    isDone = true
                    return Pair(stateList, outList)
                }

                1 -> {
                    val val1 = stateList[instPointer + 1]
                    val val2 = stateList[instPointer + 2]
                    val res = stateList[instPointer + 3]
                    val firstParam = getParameter(modeParam1, stateList, val1)
                    val secondParam = getParameter(modeParam2, stateList, val2)
                    if (modeParam3 == 1) {
                        error("write instructions can never be in immediate mode")
                    }
                    stateList[res] = firstParam + secondParam
                    instPointer += 4
                }

                2 -> {
                    val val1 = stateList[instPointer + 1]
                    val val2 = stateList[instPointer + 2]
                    val res = stateList[instPointer + 3]
                    val firstParam = getParameter(modeParam1, stateList, val1)
                    val secondParam = getParameter(modeParam2, stateList, val2)
                    if (modeParam3 == 1) {
                        error("write instructions can never be in immediate mode")
                    }
                    stateList[res] = firstParam * secondParam
                    instPointer += 4
                }

                3 -> {
                    val val1 = stateList[instPointer + 1]
                    if (!manualMode) {
                        stateList[val1] = inputList[inputIdx]
                        inputIdx++
                    } else {
                        println("Input please:")
                        stateList[val1] = readLine()!!.toInt()
                    }
                    instPointer += 2
                }

                4 -> {
                    val val1 = stateList[instPointer + 1]
                    val firstParam = getParameter(modeParam1, stateList, val1)
                    outList.add(firstParam)
                    println("Output: ${firstParam}")
                    instPointer += 2
                    if (returnOnOutput) {
                        return Pair(stateList, outList)
                    }
                }

                5 -> {
                    val val1 = stateList[instPointer + 1]
                    val val2 = stateList[instPointer + 2]
                    val firstParam = getParameter(modeParam1, stateList, val1)
                    val secondParam = getParameter(modeParam2, stateList, val2)
                    if (firstParam > 0) {
                        instPointer = secondParam
                    } else {
                        instPointer += 3
                    }
                }

                6 -> {
                    val val1 = stateList[instPointer + 1]
                    val val2 = stateList[instPointer + 2]
                    val firstParam = getParameter(modeParam1, stateList, val1)
                    val secondParam = getParameter(modeParam2, stateList, val2)
                    if (firstParam == 0) {
                        instPointer = secondParam
                    } else {
                        instPointer += 3
                    }
                }

                7 -> {
                    val val1 = stateList[instPointer + 1]
                    val val2 = stateList[instPointer + 2]
                    val res = stateList[instPointer + 3]
                    val firstParam = getParameter(modeParam1, stateList, val1)
                    val secondParam = getParameter(modeParam2, stateList, val2)
                    if (modeParam3 == 1) {
                        error("write instructions can never be in immediate mode")
                    }
                    stateList[res] = when {
                        firstParam < secondParam -> 1
                        else -> 0
                    }
                    instPointer += 4
                }

                8 -> {
                    val val1 = stateList[instPointer + 1]
                    val val2 = stateList[instPointer + 2]
                    val res = stateList[instPointer + 3]
                    val firstParam = getParameter(modeParam1, stateList, val1)
                    val secondParam = getParameter(modeParam2, stateList, val2)
                    if (modeParam3 == 1) {
                        error("write instructions can never be in immediate mode")
                    }
                    stateList[res] = when (firstParam) {
                        secondParam -> 1
                        else -> 0
                    }
                    instPointer += 4
                }

                else -> {
                    println("Done $opcode")
                    return Pair(stateList, outList)
                }
            }
        }
        println("Done End")
        return Pair(listOf(), listOf())
    }

    private fun getParameter(mode: Int, inputList: List<Int>, value: Int): Int {
        return when (mode) {
            0 -> inputList[value]
            1 -> value
            else -> error("wrong parameter")
        }
    }
}