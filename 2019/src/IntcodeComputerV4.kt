class IntcodeComputerV4(
    input: List<Long>,
    verb: Long? = null,
    noun: Long? = null,
    val manualMode: Boolean = false,
    val returnOnOutput: Boolean = false
) {
    private val stateList = input.toMutableList()
    private val addedMemory = mutableMapOf<Int, Long>()
    private val inputList = mutableListOf<Long>()
    private val outList = mutableListOf<Long>()
    private var inputIdx = 0
    private var instPointer = 0
    private var relativeBase = 0
    var isDone = false

    init {
        if (verb != null) {
            stateList[1] = verb
        }
        if (noun != null) {
            stateList[2] = noun
        }
    }

    fun copy(manualMode: Boolean = false, returnOnOutput: Boolean = false): IntcodeComputerV4 {
        val new = IntcodeComputerV4(stateList, manualMode = manualMode, returnOnOutput = returnOnOutput)
        new.addedMemory.putAll(addedMemory)
        new.instPointer = instPointer
        new.relativeBase = relativeBase
        return new
    }

    fun setMemoryAtAddress(address: Int, value: Long) {
        stateList[address] = value
    }

    fun getInputIdx() = inputIdx

    fun calculate(inputs: List<Long> = listOf()): Pair<List<Long>, List<Long>> {
        if (isDone) {
            return Pair(stateList, listOf())
        }
        inputList.addAll(inputs)
        while (instPointer < stateList.size) {
            val instruction = stateList[instPointer]
            val opcode: Int = (instruction % 100).toInt()
            val modeParam1: Int = ((instruction / 100) % 10).toInt()
            val modeParam2: Int = ((instruction / 1000) % 10).toInt()
            val modeParam3: Int = ((instruction / 10000) % 10).toInt()

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
                    val firstParam = getParameter(modeParam1, val1)
                    val secondParam = getParameter(modeParam2, val2)
                    writeValue(modeParam3, res.toInt(), firstParam + secondParam)
                    instPointer += 4
                }

                2 -> {
                    val val1 = stateList[instPointer + 1]
                    val val2 = stateList[instPointer + 2]
                    val res = stateList[instPointer + 3]
                    val firstParam = getParameter(modeParam1, val1)
                    val secondParam = getParameter(modeParam2, val2)
                    writeValue(modeParam3, res.toInt(), firstParam * secondParam)
                    instPointer += 4
                }

                3 -> {
                    val val1 = stateList[instPointer + 1]
                    if (!manualMode) {
                        writeValue(modeParam1, val1.toInt(), inputList[inputIdx])
                        inputIdx++
                    } else {
                        println("Input please:")
                        val input = readLine()!!.toLong()
                        writeValue(modeParam1, val1.toInt(), input)
                    }
                    instPointer += 2
                }

                4 -> {
                    val val1 = stateList[instPointer + 1]
                    val firstParam = getParameter(modeParam1, val1)
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
                    val firstParam = getParameter(modeParam1, val1)
                    val secondParam = getParameter(modeParam2, val2)
                    if (firstParam > 0) {
                        instPointer = secondParam.toInt()
                    } else {
                        instPointer += 3
                    }
                }

                6 -> {
                    val val1 = stateList[instPointer + 1]
                    val val2 = stateList[instPointer + 2]
                    val firstParam = getParameter(modeParam1, val1)
                    val secondParam = getParameter(modeParam2, val2)
                    if (firstParam == 0L) {
                        instPointer = secondParam.toInt()
                    } else {
                        instPointer += 3
                    }
                }

                7 -> {
                    val val1 = stateList[instPointer + 1]
                    val val2 = stateList[instPointer + 2]
                    val res = stateList[instPointer + 3]
                    val firstParam = getParameter(modeParam1, val1)
                    val secondParam = getParameter(modeParam2, val2)
                    writeValue(
                        modeParam3, res.toInt(), when {
                            firstParam < secondParam -> 1
                            else -> 0
                        }
                    )
                    instPointer += 4
                }

                8 -> {
                    val val1 = stateList[instPointer + 1]
                    val val2 = stateList[instPointer + 2]
                    val res = stateList[instPointer + 3]
                    val firstParam = getParameter(modeParam1, val1)
                    val secondParam = getParameter(modeParam2, val2)
                    writeValue(
                        modeParam3, res.toInt(), when (firstParam) {
                            secondParam -> 1
                            else -> 0
                        }
                    )
                    instPointer += 4
                }

                9 -> {
                    val val1 = stateList[instPointer + 1]
                    val firstParam = getParameter(modeParam1, val1)
                    relativeBase += firstParam.toInt()
                    instPointer += 2
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

    private fun writeValue(mode: Int, index: Int, value: Long) {
        when (mode) {
            0 -> {
                if (index >= stateList.size) {
                    addedMemory[index] = value
                } else {
                    stateList[index] = value
                }
            }

            1 -> error("write instructions can never be in immediate mode")
            2 -> {
                val idx = (index + relativeBase)
                if (idx >= stateList.size) {
                    addedMemory[idx] = value
                } else {
                    stateList[idx] = value
                }
            }
        }
    }

    private fun getParameter(mode: Int, value: Long): Long {
        return when (mode) {
            0 -> {
                if (value.toInt() >= stateList.size) {
                    addedMemory.getOrElse(value.toInt(), { 0 })
                } else {
                    stateList[value.toInt()]
                }
            }

            1 -> value
            2 -> {
                val idx = (value + relativeBase).toInt()
                if (idx >= stateList.size) {
                    addedMemory.getOrElse(idx, { 0 })
                } else {
                    stateList[idx]
                }
            }

            else -> error("wrong parameter")
        }
    }
}