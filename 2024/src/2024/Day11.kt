package `2024`

import java.io.File

fun main() {
    //val inputs = File(ClassLoader.getSystemResource("2024/dummy11.txt").toURI()).readLines()

    val inputs = File(ClassLoader.getSystemResource("2024/input11.txt").toURI()).readLines()

    Day11.part1(inputs)
    Day11.part2(inputs)
}

object Day11 {
    fun part1(input: List<String>) {
        val stoneList = input.first().split(" ").map { stone ->
            stone.toLong()
        }
        val result = blinkOptimistic(stoneList, 25)
        println(result)
    }

    private fun blinkOptimistic(stones: List<Long>, times: Int): Int {
        println(times)
        if (times == 0) {
            return stones.size
        }
        //create new stones list
        val newStonesList = stones.flatMapIndexed { index, stone ->
            if (stone == 0L) {
                listOf(1L)
            } else if (stone.toString().length % 2 == 0) {
                val stoneString = stone.toString()
                listOf(
                    stoneString.substring(0 until stoneString.length / 2).toLong(),
                    stoneString.substring(stoneString.length / 2).toLong()
                )
            } else {
                listOf(stone * 2024)
            }

        }
        return blinkOptimistic(newStonesList, times - 1)
    }

    // Solution for the second part was only possible after reading through some reddit posts about the puzzle
    fun part2(input: List<String>) {
        val stoneMap = input.first().split(" ").map { stone ->
            Pair(stone.toLong(), 1L)
        }.toMap()
        val result = blinkScalable(stoneMap, 75)
        println(result)
    }

    private fun blinkScalable(stones: Map<Long, Long>, times: Int): Long {
        println(times)
        if (times == 0) {
            return stones.map { it.value }.sum()
        }
        //create new stones map
        val newMap = stones.entries.fold(mutableMapOf<Long, Long>()) { newMap, stoneGroup ->
            val engraving = stoneGroup.key
            if (engraving == 0L) {
                newMap[1L] = newMap[1L]?.let {
                    it + stoneGroup.value
                } ?: stoneGroup.value
            } else if (engraving.toString().length % 2 == 0) {
                val firstHalf = engraving.toString().substring(0 until engraving.toString().length / 2).toLong()
                val secondHalf = engraving.toString().substring(engraving.toString().length / 2).toLong()
                newMap[firstHalf] = newMap[firstHalf]?.let {
                    it + stoneGroup.value
                } ?: stoneGroup.value
                newMap[secondHalf] = newMap[secondHalf]?.let {
                    it + stoneGroup.value
                } ?: stoneGroup.value
            } else {
                val newValue = stoneGroup.key * 2024
                newMap[newValue] = newMap[newValue]?.let {
                    it + stoneGroup.value
                } ?: stoneGroup.value
            }
            newMap
        }
        return blinkScalable(newMap, times - 1)
    }

}
