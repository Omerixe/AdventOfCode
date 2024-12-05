import java.math.BigDecimal

object Day16 {

    fun start() {
        val input = readFile(16).split(",")
        part1(input)
        part2(input)
    }

    fun part1(input: List<String>) {
        val output = listOf('a'..'p').flatten().map { it.toString() }.toMutableList()
        dance(input, output)
        println(output.joinToString(""))
    }

    fun part2(input: List<String>) {
        val output = listOf('a'..'p').flatten().map { it.toString() }.toMutableList()
        val testOutput = output.joinToString("")
        var cicle = 0
        for (i in 1..1000000000) {
            dance(input, output)
            if (output.joinToString("").equals(testOutput)) {
                cicle = i
                break
            }
        }

        val rem = BigDecimal(1000000000).rem(BigDecimal(cicle))
        for (i in 1..rem.toInt()) {
            dance(input, output)
        }

        println(output.joinToString(""))
    }

    fun dance(input: List<String>, output: MutableList<String>) {
        val outputSize = output.size - 1

        for (cmd in input) {
            val parts = cmd.substring(1).split("/")
            val kind = cmd.get(0)
            when (kind) {
                'p' -> {
                    val idxOne = output.indexOf(parts[0])
                    val idxTwo = output.indexOf(parts[1])
                    output.set(idxTwo, parts[0])
                    output.set(idxOne, parts[1])
                }

                'x' -> {
                    val tempOne = output.get(parts[0].toInt())
                    val tempTwo = output.get(parts[1].toInt())
                    output.set(parts[0].toInt(), tempTwo)
                    output.set(parts[1].toInt(), tempOne)
                }

                's' -> {
                    val temp = output.subList(outputSize - parts[0].toInt() + 1, outputSize + 1)
                    output.addAll(0, temp)
                    for (i in 1..(parts[0].toInt())) {
                        output.removeAt(16)
                    }
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    Day16.start()
}