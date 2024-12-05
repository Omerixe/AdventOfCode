package `2022`

import java.io.File

fun main() {

    val input = File(ClassLoader.getSystemResource("2022/input05.txt").toURI())
        .readLines()

    Day05.part1(input)
    Day05.part2(input)
}

object Day05 {

    private fun createInitialState(input: List<String>): List<MutableList<String>> {
        return input.fold(mutableListOf<MutableList<String>>()) { initial, line ->
            line.chunked(4).foldIndexed(initial) { index, acc, part ->
                if (part.isNotBlank()) {
                    if (acc.size > index) {
                        acc[index].add(part.trim().replace("[", "").replace("]", ""))
                    } else {
                        val currentSize = acc.size
                        (currentSize..index).forEach {
                            acc.add(mutableListOf())
                        }
                        acc[index].add(part.trim().replace("[", "").replace("]", ""))
                    }
                }
                acc
            }
        }.map { it.reversed().toMutableList() }
    }

    fun part1(input: List<String>) {
        val split = input.indexOf("")
        val first = input.subList(0, split - 1)
        val second = input.subList(split + 1, input.size)

        val result = second.fold(createInitialState(first)) { acc, line ->
            val cmds = line.split(" ")
            val amount = cmds[1].toInt()
            val source = cmds[3].toInt() - 1
            val target = cmds[5].toInt() - 1

            (1..amount).forEach {
                val element = acc[source].removeLast()
                acc[target].add(element)
            }
            acc
        }

        println("Part1: ${result.map { it.last() }.joinToString(separator = "")}")
    }

    fun part2(input: List<String>) {
        val split = input.indexOf("")
        val first = input.subList(0, split - 1)
        val second = input.subList(split + 1, input.size)

        val result = second.fold(createInitialState(first)) { acc, line ->
            val cmds = line.split(" ")
            val amount = cmds[1].toInt()
            val source = cmds[3].toInt() - 1
            val target = cmds[5].toInt() - 1

            val elements = (1..amount).map {
                acc[source].removeLast()
            }.reversed()
            acc[target].addAll(elements)
            acc
        }

        println("Part2: ${result.map { it.last() }.joinToString(separator = "")}")
    }
}
