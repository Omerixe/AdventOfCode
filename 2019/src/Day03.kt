import kotlin.math.absoluteValue

object Day03 {
    fun start() {
        val input = readFile(3)
        part1(input)
        part2(input)
    }

    fun part1(input: String) {
        val wires = input.split("\n").map { it.split(",") }
        val map = mutableMapOf(Pair(Pair(0, 0), "o"))
        var posX = 0
        var posY = 0
        for (wire1Cmd in wires[0]) {
            val op = wire1Cmd.substring(0, 1)
            val value = wire1Cmd.substring(1).toInt()

            when (op) {
                "R", "L" -> {
                    if (op == "R") {
                        for (i in 1..value) {
                            posX++
                            map.put(Pair(posX, posY), "w1")
                        }
                    } else {
                        for (i in 1..value) {
                            posX--
                            map.put(Pair(posX, posY), "w1")
                        }
                    }
                }

                "U", "D" -> {
                    if (op == "U") {
                        for (i in 1..value) {
                            posY++
                            map.put(Pair(posX, posY), "w1")
                        }
                    } else {
                        for (i in 1..value) {
                            posY--
                            map.put(Pair(posX, posY), "w1")
                        }
                    }
                }

                else -> {
                }
            }
        }

        posX = 0
        posY = 0
        for (wire2Cmd in wires[1]) {
            val op = wire2Cmd.substring(0, 1)
            val value = wire2Cmd.substring(1).toInt()

            when (op) {
                "R", "L" -> {
                    if (op == "R") {
                        for (i in 1..value) {
                            posX++
                            val pos = map.getOrDefault(Pair(posX, posY), "")
                            if (pos.isEmpty() || pos.equals("w2")) {
                                map.put(Pair(posX, posY), "w2")
                            } else {
                                map.put(Pair(posX, posY), "X")
                            }
                        }
                    } else {
                        for (i in 1..value) {
                            posX--
                            val pos = map.getOrDefault(Pair(posX, posY), "")
                            if (pos.isEmpty() || pos.equals("w2")) {
                                map.put(Pair(posX, posY), "w2")
                            } else {
                                map.put(Pair(posX, posY), "X")
                            }
                        }
                    }
                }

                "U", "D" -> {
                    if (op == "U") {
                        for (i in 1..value) {
                            posY++
                            val pos = map.getOrDefault(Pair(posX, posY), "")
                            if (pos.isEmpty() || pos.equals("w2")) {
                                map.put(Pair(posX, posY), "w2")
                            } else {
                                map.put(Pair(posX, posY), "X")
                            }
                        }
                    } else {
                        for (i in 1..value) {
                            posY--
                            val pos = map.getOrDefault(Pair(posX, posY), "")
                            if (pos.isEmpty() || pos.equals("w2")) {
                                map.put(Pair(posX, posY), "w2")
                            } else {
                                map.put(Pair(posX, posY), "X")
                            }
                        }
                    }
                }

                else -> {
                }
            }
        }
        println(map.filter { it.value.equals("X") }.map { it.key.first.absoluteValue + it.key.second.absoluteValue }
            .min())
    }

    fun part2(input: String) {
        val wires = input.split("\n").map { it.split(",") }
        val map = mutableMapOf(Pair(Pair(0, 0), "o"))
        val mapStep1 = mutableMapOf(Pair(Pair(0, 0), 0))
        val mapStep2 = mutableMapOf(Pair(Pair(0, 0), 0))

        var posX = 0
        var posY = 0
        var step = 0
        for (wire1Cmd in wires[0]) {
            val op = wire1Cmd.substring(0, 1)
            val value = wire1Cmd.substring(1).toInt()

            when (op) {
                "R", "L" -> {
                    if (op == "R") {
                        for (i in 1..value) {
                            step++
                            posX++
                            map.put(Pair(posX, posY), "w1")
                            mapStep1.putIfAbsent(Pair(posX, posY), step)
                        }
                    } else {
                        for (i in 1..value) {
                            step++
                            posX--
                            map.put(Pair(posX, posY), "w1")
                            mapStep1.putIfAbsent(Pair(posX, posY), step)
                        }
                    }
                }

                "U", "D" -> {
                    if (op == "U") {
                        for (i in 1..value) {
                            step++
                            posY++
                            map.put(Pair(posX, posY), "w1")
                            mapStep1.putIfAbsent(Pair(posX, posY), step)
                        }
                    } else {
                        for (i in 1..value) {
                            step++
                            posY--
                            map.put(Pair(posX, posY), "w1")
                            mapStep1.putIfAbsent(Pair(posX, posY), step)
                        }
                    }
                }

                else -> {
                }
            }
        }

        posX = 0
        posY = 0
        step = 0
        for (wire2Cmd in wires[1]) {
            val op = wire2Cmd.substring(0, 1)
            val value = wire2Cmd.substring(1).toInt()

            when (op) {
                "R", "L" -> {
                    if (op == "R") {
                        for (i in 1..value) {
                            step++
                            posX++
                            val pos = map.getOrDefault(Pair(posX, posY), "")
                            if (pos.isEmpty() || pos.equals("w2")) {
                                map.put(Pair(posX, posY), "w2")
                            } else {
                                map.put(Pair(posX, posY), "X")
                            }
                            mapStep2.putIfAbsent(Pair(posX, posY), step)
                        }
                    } else {
                        for (i in 1..value) {
                            step++
                            posX--
                            val pos = map.getOrDefault(Pair(posX, posY), "")
                            if (pos.isEmpty() || pos.equals("w2")) {
                                map.put(Pair(posX, posY), "w2")
                            } else {
                                map.put(Pair(posX, posY), "X")
                            }
                            mapStep2.putIfAbsent(Pair(posX, posY), step)
                        }
                    }
                }

                "U", "D" -> {
                    if (op == "U") {
                        for (i in 1..value) {
                            step++
                            posY++
                            val pos = map.getOrDefault(Pair(posX, posY), "")
                            if (pos.isEmpty() || pos.equals("w2")) {
                                map.put(Pair(posX, posY), "w2")
                            } else {
                                map.put(Pair(posX, posY), "X")
                            }
                            mapStep2.putIfAbsent(Pair(posX, posY), step)
                        }
                    } else {
                        for (i in 1..value) {
                            step++
                            posY--
                            val pos = map.getOrDefault(Pair(posX, posY), "")
                            if (pos.isEmpty() || pos.equals("w2")) {
                                map.put(Pair(posX, posY), "w2")
                            } else {
                                map.put(Pair(posX, posY), "X")
                            }
                            mapStep2.putIfAbsent(Pair(posX, posY), step)
                        }
                    }
                }

                else -> {
                }
            }
        }
        println(map.filter { it.value.equals("X") }.map { mapStep1.getValue(it.key).plus(mapStep2.getValue(it.key)) }
            .min())
    }

    fun testPart1() {
        val test = "R8,U5,L5,D3\nU7,R6,D4,L4"
        val input1 = "R75,D30,R83,U83,L12,D49,R71,U7,L72\n" +
                "U62,R66,U55,R34,D71,R55,D58,R83"
        val input2 = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\n" +
                "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"

        part1(test)
        part1(input1)
        part1(input2)
    }

    fun testPart2() {
        val test = "R8,U5,L5,D3\nU7,R6,D4,L4"
        val input1 = "R75,D30,R83,U83,L12,D49,R71,U7,L72\n" +
                "U62,R66,U55,R34,D71,R55,D58,R83"
        val input2 = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\n" +
                "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"

        part2(test)
        part2(input1)
        part2(input2)
    }
}

fun main(args: Array<String>) {
    Day03.start()
    Day03.testPart1()
    Day03.testPart2()
}