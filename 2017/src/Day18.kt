import java.math.BigDecimal

object Day18 {

    fun start() {
        val input = readFile(18)
        val commands = input.split("\n")
        part1(commands)
    }

    fun part1(commands: List<String>) {
        val registers: MutableMap<String, BigDecimal> = mutableMapOf()
        var frequency = BigDecimal.ZERO
        var i = 0

        while (i < commands.size) {
            println(commands[i])
            val parts = commands[i].split(" ")
            val command = parts[0]
            val first = registers.getOrDefault(parts[1], BigDecimal(parts[1].toIntOrNull() ?: 0))
            var second = BigDecimal.ZERO
            if (parts.size > 2) {
                second = registers.getOrDefault(parts[2], BigDecimal(parts[2].toIntOrNull() ?: 0))
            }
            var stop = false
            var jump = false
            when (command) {
                "snd" -> {
                    frequency = first
                    println(frequency)
                }

                "set" -> {
                    registers.put(parts[1], second)
                }

                "add" -> {
                    registers.put(parts[1], first + second)
                }

                "mul" -> {
                    registers.put(parts[1], first * second)
                }

                "mod" -> {
                    if (second.compareTo(BigDecimal.ZERO) >= 0) {
                        registers.put(parts[1], first % second)
                    }
                }

                "rcv" -> {
                    if (!first.equals(BigDecimal.ZERO)) {
                        println("stop")
                        println(frequency)
                        stop = true
                    }
                }

                "jgz" -> {
                    if (first.compareTo(BigDecimal.ZERO) >= 1) {
                        i = i + second.toInt()
                        jump = true
                    }
                }

            }
            println(registers)
            if (stop) break
            if (jump) continue
            i++
        }
    }

    fun part2(commands: List<String>) {
        val registers0: MutableMap<String, BigDecimal> = mutableMapOf()
        val registers1: MutableMap<String, BigDecimal> = mutableMapOf()
        var frequency0 = BigDecimal.ZERO
        var frequency1 = BigDecimal.ZERO

        var idx1 = 0
        var idx2 = 0

        while (idx1 < commands.size || idx2 < commands.size) {

        }
    }

}

fun main(args: Array<String>) {
    Day18.start()
}