object Day11 {

    fun start() {
        val input = readFile(11).split(",").map { it.toLong() }
        part1(input)
        part2(input)
    }

    fun part1(input: List<Long>) {
        val robo = IntcodeComputerV4(input, manualMode = false, returnOnOutput = true)
        val map: MutableMap<Pair<Int, Int>, Long> = mutableMapOf()
        var x = 0
        var y = 0
        var orientation = 0
        while (!robo.isDone) {
            val value1 = robo.calculate(listOf(map.getOrDefault(Pair(x, y), 0L))).second.last()
            if (robo.isDone) break
            val value2 = robo.calculate().second.last()
            if (value1 == 0L) {
                map.put(Pair(x, y), 0L)
            } else {
                map.put(Pair(x, y), 1L)
            }
            when (value2) {
                0L -> {
                    when (orientation) {
                        0 -> {
                            orientation += 90
                            x += 1
                        }

                        90 -> {
                            orientation += 90
                            y -= 1
                        }

                        180 -> {
                            orientation += 90
                            x -= 1
                        }

                        270 -> {
                            orientation = 0
                            y += 1
                        }
                    }
                }

                1L -> {
                    when (orientation) {
                        0 -> {
                            orientation = 270
                            x -= 1
                        }

                        90 -> {
                            orientation -= 90
                            y += 1
                        }

                        180 -> {
                            orientation -= 90
                            x += 1
                        }

                        270 -> {
                            orientation -= 90
                            y -= 1
                        }
                    }
                }
            }
        }
        println(map.size)
    }

    fun part2(input: List<Long>) {
        val robo = IntcodeComputerV4(input, manualMode = false, returnOnOutput = true)
        val map: MutableMap<Pair<Int, Int>, Long> = mutableMapOf()
        var x = 0
        var y = 0
        var orientation = 0
        var start = true
        while (!robo.isDone) {
            val value1 = robo.calculate(listOf(map.getOrDefault(Pair(x, y), if (start) 1L else 0L))).second.last()
            start = false
            if (robo.isDone) break
            val value2 = robo.calculate().second.last()
            if (value1 == 0L) {
                map[Pair(x, y)] = 0L
            } else {
                map[Pair(x, y)] = 1L
            }
            when (value2) {
                0L -> {
                    when (orientation) {
                        0 -> {
                            orientation += 90
                            x += 1
                        }

                        90 -> {
                            orientation += 90
                            y -= 1
                        }

                        180 -> {
                            orientation += 90
                            x -= 1
                        }

                        270 -> {
                            orientation = 0
                            y += 1
                        }
                    }
                }

                1L -> {
                    when (orientation) {
                        0 -> {
                            orientation = 270
                            x -= 1
                        }

                        90 -> {
                            orientation -= 90
                            y += 1
                        }

                        180 -> {
                            orientation -= 90
                            x += 1
                        }

                        270 -> {
                            orientation -= 90
                            y -= 1
                        }
                    }
                }
            }
        }
        println(map.size)
        printOutput(map)
    }

    fun printOutput(input: Map<Pair<Int, Int>, Long>) {
        val inputList = input.toList()
        val minX = inputList.minBy { it.first.first }!!.first.first
        val maxX = inputList.maxBy { it.first.first }!!.first.first
        val minY = inputList.minBy { it.first.second }!!.first.second
        val maxY = inputList.maxBy { it.first.second }!!.first.second
        var y = maxY
        var x = maxX
        while (y >= minY) {
            while (x >= minX) {
                val paint = input.getOrDefault(Pair(x, y), 0L)
                if (paint == 0L) {
                    print("  ")
                } else {
                    print("\u2591\u2591")
                }
                x -= 1
            }
            y -= 1
            x = maxX
            println()
        }
    }
}

fun main(args: Array<String>) {
    Day11.start()
}