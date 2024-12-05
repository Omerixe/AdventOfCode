package `2023`

import java.io.File

fun main() {
    //val inputs = File(ClassLoader.getSystemResource("2023/dummy07.txt").toURI()).readLines()

    val inputs = File(ClassLoader.getSystemResource("2023/input07.txt").toURI()).readLines()
    Day07.part1(inputs)
    Day07.part2(inputs)
}

object Day07 {
    fun part1(inputs: List<String>) {
        val fiveKind = mutableListOf<Pair<String, Int>>()
        val fourKind = mutableListOf<Pair<String, Int>>()
        val fullHouse = mutableListOf<Pair<String, Int>>()
        val threeKind = mutableListOf<Pair<String, Int>>()
        val twoPair = mutableListOf<Pair<String, Int>>()
        val onePair = mutableListOf<Pair<String, Int>>()
        val highCard = mutableListOf<Pair<String, Int>>()

        inputs.map { line ->
            val parts = line.split(" ")
            val hand =
                parts.first().replace("A", "E").replace("K", "D").replace("Q", "C").replace("J", "B").replace("T", "A")
            Pair(hand, parts.last().toInt())
        }.forEach { hand ->
            val groups = hand.first.groupBy { it }
            when (groups.size) {
                1 -> fiveKind.add(hand)
                2 -> {
                    val sorted = groups.values.sortedByDescending { it.size }
                    if (sorted.first().size == 4) {
                        fourKind.add(hand)
                    } else {
                        fullHouse.add(hand)
                    }
                }

                3 -> {
                    val sorted = groups.values.sortedByDescending { it.size }
                    if (sorted.first().size == 3) {
                        threeKind.add(hand)
                    } else {
                        twoPair.add(hand)
                    }
                }

                4 -> onePair.add(hand)
                else -> highCard.add(hand)
            }
        }

        val finalOrder =
            highCard.sortedBy { it.first }.plus(onePair.sortedBy { it.first }).plus(twoPair.sortedBy { it.first })
                .plus(threeKind.sortedBy { it.first }).plus(fullHouse.sortedBy { it.first })
                .plus(fourKind.sortedBy { it.first }).plus(fiveKind.sortedBy { it.first })
        val res = finalOrder.mapIndexed { index, hand ->
            hand.second * (index + 1)
        }.sum()
        println("Part 1: $res")
    }

    fun part2(inputs: List<String>) {
        val fiveKind = mutableListOf<Pair<String, Int>>()
        val fourKind = mutableListOf<Pair<String, Int>>()
        val fullHouse = mutableListOf<Pair<String, Int>>()
        val threeKind = mutableListOf<Pair<String, Int>>()
        val twoPair = mutableListOf<Pair<String, Int>>()
        val onePair = mutableListOf<Pair<String, Int>>()
        val highCard = mutableListOf<Pair<String, Int>>()

        inputs.map { line ->
            val parts = line.split(" ")
            val hand =
                parts.first().replace("A", "E").replace("K", "D").replace("Q", "C").replace("J", "0").replace("T", "A")
            Pair(hand, parts.last().toInt())
        }.forEach { hand ->

            val groups = hand.first.groupBy { it }
            val numberOfJokers = groups['0']?.size
            when (groups.size) {
                1 -> fiveKind.add(hand)
                2 -> {
                    val sorted = groups.values.sortedByDescending { it.size }
                    if (sorted.first().size == 4) {
                        if (numberOfJokers == 1 || numberOfJokers == 4) {
                            fiveKind.add(hand)
                        } else {
                            fourKind.add(hand)
                        }
                    } else {
                        if (numberOfJokers != null && numberOfJokers > 0) {
                            fiveKind.add(hand)
                        } else {
                            fullHouse.add(hand)
                        }
                    }
                }

                3 -> {
                    val sorted = groups.values.sortedByDescending { it.size }
                    if (sorted.first().size == 3) {
                        if (numberOfJokers == 3 || numberOfJokers == 1) {
                            fourKind.add(hand)
                        } else {
                            threeKind.add(hand)
                        }
                    } else {
                        if (numberOfJokers == 2) {
                            fourKind.add(hand)
                        } else if (numberOfJokers == 1) {
                            fullHouse.add(hand)
                        } else {
                            twoPair.add(hand)
                        }
                    }
                }

                4 -> {
                    if (numberOfJokers == 1) {
                        threeKind.add(hand)
                    } else if (numberOfJokers == 2) {
                        threeKind.add(hand)
                    } else {
                        onePair.add(hand)
                    }
                }

                else -> {
                    if (numberOfJokers == 1) {
                        onePair.add(hand)
                    } else {
                        highCard.add(hand)
                    }
                }
            }
        }

        val finalOrder =
            highCard.sortedBy { it.first }.plus(onePair.sortedBy { it.first }).plus(twoPair.sortedBy { it.first })
                .plus(threeKind.sortedBy { it.first }).plus(fullHouse.sortedBy { it.first })
                .plus(fourKind.sortedBy { it.first }).plus(fiveKind.sortedBy { it.first })
        val res = finalOrder.mapIndexed { index, hand ->
            //println(hand.first.replace("A", "T").replace("E", "A").replace("D", "K").replace("C", "Q").replace("0", "J"))
            hand.second * (index + 1)
        }.sum()
        println("Part 2: $res")
        // 307816087 - too high
        // 253491707 - too high
        // 253901872
        // 253491707
        // 253473930 - finally
    }
}
