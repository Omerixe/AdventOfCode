import kotlin.math.absoluteValue

object Day07 {

    fun start() {
        val input = readFile(7).split(",").map { it.toInt() }
        //println(part1(input))
        println(part2(input))
    }

    fun part1(input: List<Int>): Int {
        val sequences = createSequences(0, 4)
        val outputs = mutableListOf<Int>()
        for (sequence in sequences) {
            //Amp A
            println("Amp A:")
            val resultA = IntcodeComputer(input).calculate(listOf(sequence[0], 0))
            //Amp B
            println("Amp B:")
            val resultB = IntcodeComputer(input).calculate(listOf(sequence[1], resultA.second.last()))
            //Amp C
            println("Amp C:")
            val resultC = IntcodeComputer(input).calculate(listOf(sequence[2], resultB.second.last()))
            //Amp D
            println("Amp D:")
            val resultD = IntcodeComputer(input).calculate(listOf(sequence[3], resultC.second.last()))
            //Amp E
            println("Amp E:")
            val resultE = IntcodeComputer(input).calculate(listOf(sequence[4], resultD.second.last()))
            outputs.add(resultE.second.last())
        }
        return outputs.max()!!
    }

    fun part2(input: List<Int>): Int {
        val sequences = createSequences(5, 9)
        val outputs = mutableListOf<Int>()
        for (sequence in sequences) {
            var finished = false
            var lastOut = 0
            var firstRound = true

            val ampA = IntcodeComputer(input, returnOnOutput = true)
            val ampB = IntcodeComputer(input, returnOnOutput = true)
            val ampC = IntcodeComputer(input, returnOnOutput = true)
            val ampD = IntcodeComputer(input, returnOnOutput = true)
            val ampE = IntcodeComputer(input, returnOnOutput = true)

            while (!finished) {
                if (firstRound) {
                    val resultA = ampA.calculate(listOf(sequence[0], lastOut))
                    val resultB = ampB.calculate(listOf(sequence[1], resultA.second.last()))
                    val resultC = ampC.calculate(listOf(sequence[2], resultB.second.last()))
                    val resultD = ampD.calculate(listOf(sequence[3], resultC.second.last()))
                    val resultE = ampE.calculate(listOf(sequence[4], resultD.second.last()))
                    lastOut = resultE.second.last()
                    firstRound = false
                } else {
                    val resultA = ampA.calculate(listOf(lastOut))
                    val resultB = ampB.calculate(listOf(resultA.second.last()))
                    val resultC = ampC.calculate(listOf(resultB.second.last()))
                    val resultD = ampD.calculate(listOf(resultC.second.last()))
                    val resultE = ampE.calculate(listOf(resultD.second.last()))
                    lastOut = resultE.second.last()
                }

                if (ampE.isDone) {
                    finished = true
                }
            }
            outputs.add(lastOut)
        }
        return outputs.max()!!
    }

    private fun createSequences(from: Int, to: Int): List<List<Int>> {
        val output = mutableSetOf<List<Int>>()
        for (n1 in from..to) {
            for (n2 in from..to) {
                for (n3 in from..to) {
                    for (n4 in from..to) {
                        for (n5 in from..to) {
                            if (setOf(n1, n2, n3, n4, n5).size == (to - from + 1).absoluteValue) {
                                output.add(listOf(n1, n2, n3, n4, n5))
                            }
                        }
                    }
                }
            }
        }
        return output.toList()
    }

    fun test1() {
        val input1 = listOf(3, 15, 3, 16, 1002, 16, 10, 16, 1, 16, 15, 15, 4, 15, 99, 0, 0)
        val sequence1 = listOf(4, 3, 2, 1, 0)

        val input2 = listOf(
            3, 23, 3, 24, 1002, 24, 10, 24, 1002, 23, -1, 23,
            101, 5, 23, 23, 1, 24, 23, 23, 4, 23, 99, 0, 0
        )
        val sequence2 = listOf(0, 1, 2, 3, 4)

        val input3 = listOf(
            3, 31, 3, 32, 1002, 32, 10, 32, 1001, 31, -2, 31, 1007, 31, 0, 33,
            1002, 33, 7, 33, 1, 33, 31, 31, 1, 32, 31, 31, 4, 31, 99, 0, 0, 0
        )
        val sequence3 = listOf(1, 0, 4, 3, 2)

        val inputs = listOf(Pair(input1, sequence1), Pair(input2, sequence2), Pair(input3, sequence3))
        val outputs = mutableListOf<Int>()
        for (input in inputs) {
            //Amp A
            println("Amp A:")
            val resultA = IntcodeComputer(input.first).calculate(listOf(input.second[0], 0))
            //Amp B
            println("Amp B:")
            val resultB = IntcodeComputer(input.first).calculate(listOf(input.second[1], resultA.second.last()))
            //Amp C
            println("Amp C:")
            val resultC = IntcodeComputer(input.first).calculate(listOf(input.second[2], resultB.second.last()))
            //Amp D
            println("Amp D:")
            val resultD = IntcodeComputer(input.first).calculate(listOf(input.second[3], resultC.second.last()))
            //Amp E
            println("Amp E:")
            val resultE = IntcodeComputer(input.first).calculate(listOf(input.second[4], resultD.second.last()))
            outputs.add(resultE.second.last())
        }
        println(outputs)
    }

    fun test2() {
        val input1 = listOf(
            3, 26, 1001, 26, -4, 26, 3, 27, 1002, 27, 2, 27, 1, 27, 26,
            27, 4, 27, 1001, 28, -1, 28, 1005, 28, 6, 99, 0, 0, 5
        )
        val sequence1 = listOf(9, 8, 7, 6, 5)

        val input2 = listOf(
            3, 52, 1001, 52, -5, 52, 3, 53, 1, 52, 56, 54, 1007, 54, 5, 55, 1005, 55, 26, 1001, 54,
            -5, 54, 1105, 1, 12, 1, 53, 54, 53, 1008, 54, 0, 55, 1001, 55, 1, 55, 2, 53, 55, 53, 4,
            53, 1001, 56, -1, 56, 1005, 56, 6, 99, 0, 0, 0, 0, 10
        )
        val sequence2 = listOf(9, 7, 8, 5, 6)
        val inputs = listOf(Pair(input1, sequence1), Pair(input2, sequence2))

        val outputs = mutableListOf<Int>()

        for (input in inputs) {
            var finished = false
            var lastOut = 0
            var firstRound = true
            val ampA = IntcodeComputer(input.first, returnOnOutput = true)
            val ampB = IntcodeComputer(input.first, returnOnOutput = true)
            val ampC = IntcodeComputer(input.first, returnOnOutput = true)
            val ampD = IntcodeComputer(input.first, returnOnOutput = true)
            val ampE = IntcodeComputer(input.first, returnOnOutput = true)

            while (!finished) {
                if (firstRound) {
                    val resultA = ampA.calculate(listOf(input.second[0], lastOut))
                    val resultB = ampB.calculate(listOf(input.second[1], resultA.second.last()))
                    val resultC = ampC.calculate(listOf(input.second[2], resultB.second.last()))
                    val resultD = ampD.calculate(listOf(input.second[3], resultC.second.last()))
                    val resultE = ampE.calculate(listOf(input.second[4], resultD.second.last()))
                    lastOut = resultE.second.last()
                    firstRound = false
                } else {
                    val resultA = ampA.calculate(listOf(lastOut))
                    val resultB = ampB.calculate(listOf(resultA.second.last()))
                    val resultC = ampC.calculate(listOf(resultB.second.last()))
                    val resultD = ampD.calculate(listOf(resultC.second.last()))
                    val resultE = ampE.calculate(listOf(resultD.second.last()))
                    lastOut = resultE.second.last()
                }

                if (ampE.isDone) {
                    finished = true
                }
            }
            outputs.add(lastOut)
        }
        println(outputs)
    }
}

fun main(args: Array<String>) {
    Day07.start()
}