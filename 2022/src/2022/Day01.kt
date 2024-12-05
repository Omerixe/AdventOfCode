package `2022`

import java.io.File

fun main() {

    val numbers = File(ClassLoader.getSystemResource("2022/input01.txt").toURI())
        .readLines()

    part1(numbers)
    part2(numbers)
}

fun part1(numbers: List<String>) {
    // First try
    /*val newlist = mutableListOf<Int>()
    var sum = 0
    numbers.forEach {
        if (it.isNotEmpty()) {
            sum += it.toInt()
        } else {
            newlist.add(sum)
            sum = 0
        }
    }
    newlist.add(sum)
    newlist.sortDescending()
    println(newlist.first() )*/

    //Second try
    val newlist: List<Int> = numbers.fold(mutableListOf(0)) { list, element ->
        if (element.isBlank()) {
            list.add(0)
        } else {
            list[list.size - 1] = list[list.size - 1] + element.toInt()
        }
        list
    }.sortedDescending()
    println(newlist.first())
}

fun part2(numbers: List<String>) {
    val newlist = mutableListOf<Int>()
    var sum = 0
    numbers.forEach {
        if (it.isNotEmpty()) {
            sum += it.toInt()
        } else {
            newlist.add(sum)
            sum = 0
        }
    }
    newlist.add(sum)
    newlist.sortDescending()
    println(newlist.take(3).sum())
}