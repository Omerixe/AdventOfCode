package `2024`

import java.io.File

fun main() {
    //val inputs = File(ClassLoader.getSystemResource("2024/dummy09.txt").toURI()).readLines()

    val inputs = File(ClassLoader.getSystemResource("2024/input09.txt").toURI()).readLines()

    Day09.part1(inputs)
    Day09.part2(inputs)
}

object Day09 {
    fun part1(input: List<String>) {
        val inputFormatted = input.first().flatMapIndexed { index, char ->
            if (index % 2 == 1) {
                // free space
                (0 until char.toString().toInt()).map { -1 }
            } else {
                // block space
                (0 until char.toString().toInt()).map { index / 2 }
            }
        }
        val filler = inputFormatted.filter { it > -1 }.toMutableList()
        val result = inputFormatted.mapIndexedNotNull { index, element ->
            if (element == -1) {
                if (filler.isNotEmpty()) {
                    index.toLong() * filler.removeAt(filler.size - 1)
                } else {
                    null
                }
            } else {
                val elemIndex = filler.indexOf(element)
                if (elemIndex > -1) {
                    index.toLong() * filler.removeAt(elemIndex)
                } else {
                    null
                }
            }
        }.sum()

        println(result)
    }

    fun part2(input: List<String>) {
        val inputFormatted = input.first().mapIndexed { index, char ->
            if (index % 2 == 1) {
                // free space
                Pair(char.toString().toInt(), -1)
            } else {
                // block space
                Pair(char.toString().toInt(), index / 2)
            }
        }.toMutableList()

        val fillerList = inputFormatted.filter { it.second > -1 }.asReversed()
        fillerList.forEach { filler ->
            val indexToPut = inputFormatted.indexOfFirst { it.second == -1 && it.first >= filler.first }
            val indexOfFiller = inputFormatted.indexOf(filler)

            // Check if there is an empty space big enough
            if (indexToPut in 0 until indexOfFiller) {
                val gap = inputFormatted[indexToPut]
                if (gap.first > filler.first) {
                    inputFormatted[indexOfFiller] = Pair(filler.first, -1)
                    inputFormatted[indexToPut] = filler
                    inputFormatted.add(indexToPut + 1, Pair(gap.first - filler.first, -1))
                } else {
                    inputFormatted[indexOfFiller] = gap
                    inputFormatted[indexToPut] = filler
                }
            }
        }
        val result = inputFormatted
            .flatMap { pair -> List(pair.first) { pair.second.toLong() } }
            .mapIndexedNotNull() { index, id -> if (id != -1L) index * id else null }
            .sum()
        println(result)
    }
}
