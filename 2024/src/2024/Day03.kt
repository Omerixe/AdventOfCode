package `2024`

import java.io.File
import java.math.BigDecimal

fun main() {
    //val inputs = File(ClassLoader.getSystemResource("2024/dummy03.txt").toURI()).readLines()

    val inputs = File(ClassLoader.getSystemResource("2024/input03.txt").toURI()).readLines()

    Day03.part1(inputs)
    Day03.part2(inputs)
}

// ((do\(\)|^(don't\(\)))+.*(mul\((\d+),(\d+)\))*)
object Day03 {
    fun part1(input: List<String>) {
        val regex = "(mul\\((\\d+),(\\d+)\\))".toRegex()
        val result = input.flatMap { line ->
            regex.findAll(line).asIterable().map { res ->
                res.groups[2]!!.value.toBigDecimal() * res.groups[3]!!.value.toBigDecimal()
            }
        }.fold(BigDecimal(0)) { initial, element ->
            initial + element
        }
        println(result)
    }

    fun part2(input: List<String>) {
        val regex = "((mul\\(\\d+,\\d+\\))|(do\\(\\))|(don't\\(\\)))".toRegex()
        val regex2 = "mul\\((\\d+),(\\d+)\\)".toRegex()
        val result = input.flatMap { line ->
            regex.findAll(line).asIterable().map { res ->
                res.groups[0]!!.value
            }
        }.fold(Pair(listOf<String>(), true)) { acc, element ->
            when {
                element == "do()" -> Pair(acc.first, true)
                element == "don't()" -> Pair(acc.first, false)
                acc.second -> Pair(acc.first + element, true)
                else -> acc
            }
        }.first.flatMap { mul ->
            regex2.findAll(mul).asIterable().map { res ->
                res.groupValues[1].toBigDecimal() * res.groupValues[2].toBigDecimal()
            }
        }.fold(BigDecimal(0)) { initial, element ->
            initial + element
        }
        println(result)
    }
}
