import kotlin.math.absoluteValue

object Day13 {

    fun start() {
        val input = readFile(13).split(",").map { it.toLong() }
        //part1(input)
        part2(input)
    }


    fun part1(input: List<Long>) {
        val pc = IntcodeComputerV4(input, returnOnOutput = true)
        val game: MutableMap<Pair<Long, Long>, Long> = mutableMapOf()

        while (!pc.isDone) {
            val x = pc.calculate().second.last()
            if (pc.isDone) break
            val y = pc.calculate().second.last()
            val tile = pc.calculate().second.last()

            game[Pair(x, y)] = tile
        }

        println(game.toList().filter { it.second == 2L }.size)
        printGame(game)
    }

    fun part2(input: List<Long>) {
        val pc = IntcodeComputerV4(input, manualMode = true, returnOnOutput = true)
        pc.setMemoryAtAddress(0, 2)
        val game: MutableMap<Pair<Long, Long>, Long> = mutableMapOf()

        while (!pc.isDone) {
            println("round")
            val x = pc.calculate().second.last()
            if (pc.isDone) break
            val y = pc.calculate().second.last()
            val tile = pc.calculate().second.last()

            if (x == -1L && y == 0L) {
                println("Score: $tile")
            } else {
                game[Pair(x, y)] = tile
            }
            printGame(game)
        }
    }

    private fun letItGame(pc: IntcodeComputerV4, paddlePos: Int): MutableList<Long> {
        val pseudoInput = List(1000) { 0L }
        val result = pc.calculate(pseudoInput)
        val neededPaddlePos = result.second[result.second.size - 12]
        println("paddle $paddlePos needed: $neededPaddlePos")
        val paddleDiff = neededPaddlePos - paddlePos
        val inputCount = pc.getInputIdx() - 1
        val inputDiff = inputCount - paddleDiff.absoluteValue
        val first = MutableList(inputDiff.toInt() + 1) { 0L }
        val second = List(paddleDiff.toInt().absoluteValue - 1) {
            if (paddleDiff > 0) {
                1L
            } else {
                -1L
            }
        }
        first.addAll(second)
        println(first)
        return first
    }

    private fun printGame(game: MutableMap<Pair<Long, Long>, Long>) {
        val maxX = game.toList().maxBy { it.first.first }!!.first.first
        val maxY = game.toList().maxBy { it.first.second }!!.first.second
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                when (game.getOrDefault(Pair(x, y), 0)) {
                    0L -> print(" ")
                    1L -> {
                        if (x == 0L || x == maxX) {
                            if (y == 0L || y == maxY) {
                                print("+")
                            } else {
                                print("|")
                            }
                        } else {
                            print("-")
                        }
                    }

                    2L -> print("░")
                    3L -> print("_")
                    4L -> print("o")
                }
            }
            println()
        }
    }

    fun test() {
        val input = readFile(13).split(",").map { it.toLong() }
        val pc = IntcodeComputerV4(input, manualMode = false, returnOnOutput = false)
        pc.setMemoryAtAddress(0, 2)
        letItGame(pc, 20)
    }
}

fun main(args: Array<String>) {
    Day13.start()
}