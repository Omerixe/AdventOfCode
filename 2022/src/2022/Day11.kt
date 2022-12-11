package `2022`

import java.io.File
import kotlin.math.abs

fun main() {
    val dummyMonkeys = Day11::initDummyMonkeys
    val realMonkeys =  Day11::initRealMonkeys

    val input = realMonkeys

    Day11.part1(input())
    Day11.part2(input())
}

data class Monkey(
    val idx: Int,
    val startingItems: MutableList<Long>,
    val operation: (Long) -> Long,
    val divisor: Int,
    val positiveThrow: Int,
    val negativeThrow: Int
)

object Day11 {

    fun initDummyMonkeys() = listOf(
        Monkey(
            idx = 0,
            startingItems = mutableListOf(79, 98),
            operation = { it * 19 },
            divisor = 23,
            positiveThrow = 2,
            negativeThrow = 3
        ),
        Monkey(
            idx = 1,
            startingItems = mutableListOf(54, 65, 75, 74),
            operation = { it + 6 },
            divisor = 19,
            positiveThrow = 2,
            negativeThrow = 0
        ),
        Monkey(
            idx = 2,
            startingItems = mutableListOf(79, 60, 97),
            operation = { it * it },
            divisor = 13,
            positiveThrow = 1,
            negativeThrow = 3
        ),
        Monkey(
            idx = 3,
            startingItems = mutableListOf(74),
            operation = { it + 3 },
            divisor = 17,
            positiveThrow = 0,
            negativeThrow = 1
        ),
    )

    fun initRealMonkeys() = listOf(
        Monkey(
            idx = 0,
            startingItems = mutableListOf(73, 77),
            operation = { it * 5 },
            divisor = 11,
            positiveThrow = 6,
            negativeThrow = 5
        ),
        Monkey(
            idx = 1,
            startingItems = mutableListOf(57, 88, 80),
            operation = { it + 5 },
            divisor = 19,
            positiveThrow = 6,
            negativeThrow = 0
        ),
        Monkey(
            idx = 2,
            startingItems = mutableListOf(61, 81, 84, 69, 77, 88),
            operation = { it * 19 },
            divisor = 5,
            positiveThrow = 3,
            negativeThrow = 1
        ),
        Monkey(
            idx = 3,
            startingItems = mutableListOf(78, 89, 71, 60, 81, 84, 87, 75),
            operation = { it + 7 },
            divisor = 3,
            positiveThrow = 1,
            negativeThrow = 0
        ),
        Monkey(
            idx = 4,
            startingItems = mutableListOf(60, 76, 90, 63, 86, 87, 89),
            operation = { it + 2 },
            divisor = 13,
            positiveThrow = 2,
            negativeThrow = 7
        ),
        Monkey(
            idx = 5,
            startingItems = mutableListOf(88),
            operation = { it + 1 },
            divisor = 17,
            positiveThrow = 4,
            negativeThrow = 7
        ),
        Monkey(
            idx = 6,
            startingItems = mutableListOf(84, 98, 78, 85),
            operation = { it * it },
            divisor = 7,
            positiveThrow = 5,
            negativeThrow = 4
        ),
        Monkey(
            idx = 7,
            startingItems = mutableListOf(98, 89, 78, 73, 71),
            operation = { it + 4 },
            divisor = 2,
            positiveThrow = 3,
            negativeThrow = 2
        ),
    )

    fun part1(input: List<Monkey>) {
        val rounds = 20
        val monkeyInspection = (input.indices).map { 0 }.toMutableList()
        val monkeys = input.toMutableList()

        (1..rounds).forEach { round ->
            (monkeys.indices).forEach { monkeyIdx ->
                val currentMonkey = monkeys[monkeyIdx]
                currentMonkey.startingItems.forEach { item ->
                    val newWorryLevel = currentMonkey.operation(item).div(3)
                    if (newWorryLevel.mod(currentMonkey.divisor) == 0) {
                        monkeys[currentMonkey.positiveThrow].startingItems.add(newWorryLevel)
                        monkeys[currentMonkey.positiveThrow] = monkeys[currentMonkey.positiveThrow].copy()
                    } else {
                        monkeys[currentMonkey.negativeThrow].startingItems.add(newWorryLevel)
                    }
                    monkeyInspection[monkeyIdx]++
                }
                currentMonkey.startingItems.removeAll { true }
            }
        }

        val res = monkeyInspection.sortedDescending().take(2).fold(1) { acc, item -> acc * item }

        println("Part1: $res")
    }

    fun part2(input: List<Monkey>) {
        val rounds = 10000
        val monkeyInspection = (input.indices).map { 0L }.toMutableList()
        val monkeys = input.toMutableList()
        val masterMod = monkeys.map { it.divisor }.reduce { acc, i -> acc * i }

        (1..rounds).forEach { round ->
            (monkeys.indices).forEach { monkeyIdx ->
                val currentMonkey = monkeys[monkeyIdx]
                currentMonkey.startingItems.forEach { item ->
                    // I had to look up the solution with the the mod operation :(
                    val newWorryLevel = currentMonkey.operation(item).mod(masterMod).toLong()
                    if (newWorryLevel < 0) println("$round - $newWorryLevel")
                    if (newWorryLevel.mod(currentMonkey.divisor) == 0) {
                        monkeys[currentMonkey.positiveThrow].startingItems.add(newWorryLevel)
                    } else {
                        monkeys[currentMonkey.negativeThrow].startingItems.add(newWorryLevel)
                    }
                    monkeyInspection[monkeyIdx]++
                }
                currentMonkey.startingItems.removeAll { true }
            }
        }

        val res = monkeyInspection.sortedDescending().take(2).fold(1L) { acc, item -> acc * item }
        println("Part2: $res")
    }


}
