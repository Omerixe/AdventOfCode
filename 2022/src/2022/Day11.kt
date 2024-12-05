package `2022`

fun main() {
    val dummyMonkeys = Day11::initDummyMonkeys
    val realMonkeys = Day11::initRealMonkeys

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

    // Add again from input
    fun initRealMonkeys() = listOf(
        Monkey(
            idx = 0,
            startingItems = mutableListOf(),
            operation = { 0 },
            divisor = 0,
            positiveThrow = 0,
            negativeThrow = 0
        ),
        Monkey(
            idx = 1,
            startingItems = mutableListOf(),
            operation = { 0 },
            divisor = 0,
            positiveThrow = 0,
            negativeThrow = 0
        ),
        Monkey(
            idx = 2,
            startingItems = mutableListOf(),
            operation = { 0 },
            divisor = 0,
            positiveThrow = 0,
            negativeThrow = 0
        ),
        Monkey(
            idx = 3,
            startingItems = mutableListOf(),
            operation = { 0 },
            divisor = 0,
            positiveThrow = 0,
            negativeThrow = 0
        ),
        Monkey(
            idx = 4,
            startingItems = mutableListOf(),
            operation = { 0 },
            divisor = 0,
            positiveThrow = 0,
            negativeThrow = 0
        ),
        Monkey(
            idx = 5,
            startingItems = mutableListOf(),
            operation = { 0 },
            divisor = 0,
            positiveThrow = 0,
            negativeThrow = 0
        ),
        Monkey(
            idx = 6,
            startingItems = mutableListOf(),
            operation = { 0 },
            divisor = 0,
            positiveThrow = 0,
            negativeThrow = 0
        ),
        Monkey(
            idx = 7,
            startingItems = mutableListOf(),
            operation = { 0 },
            divisor = 0,
            positiveThrow = 0,
            negativeThrow = 0
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
