package `2021`

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.io.File

suspend fun main() {

    val input = File(ClassLoader.getSystemResource("2021/input06.txt").toURI()).readLines().first().split(",")

    var tempInput = input.map(String::toInt)
    (0 until 80).forEach {
        tempInput = tempInput.map { fish ->
            if (fish == 0) {
                listOf(6, 8)
            } else {
                listOf(fish - 1)
            }
        }.flatten()
    }
    println(tempInput.size)

    //Does end in a heap overflow
    /*val result2 = input.pmap {
        var tempInput2 = listOf<Int>(it.toInt())
        (0 until 256).forEach {
            tempInput2 = tempInput2.map { fish ->
                if (fish == 0) {
                    listOf(6,8)
                } else {
                    listOf(fish-1)
                }
            }.flatten()
        }
        tempInput2.size
    }.sum()
    println(result2)

    //Does end in a GC overhead limit exceeded
    val blub = (0 until 9).map {
        var tempInput2 = listOf(0)
        (0 until 256).forEach {
            tempInput2 = tempInput2.map { fish ->
                if (fish == 0) {
                    listOf(6,8)
                } else {
                    listOf(fish-1)
                }
            }.flatten()
        }
        tempInput2.size
    }*/

    //I couldn't figure out how to solve it
    // Solution that I got from the megathread in reddit
    // It is so easy and fast, sad that I couldn't figure it out on my own.. maybe next time ;)

    // Group all the fishes with the same state together
    val groups = input.groupingBy { it }.eachCount()
    var t0 = groups["0"]?.toLong() ?: 0L
    var t1 = groups["1"]?.toLong() ?: 0L
    var t2 = groups["2"]?.toLong() ?: 0L
    var t3 = groups["3"]?.toLong() ?: 0L
    var t4 = groups["4"]?.toLong() ?: 0L
    var t5 = groups["5"]?.toLong() ?: 0L
    var t6 = groups["6"]?.toLong() ?: 0L
    var t7 = groups["7"]?.toLong() ?: 0L
    var t8 = groups["8"]?.toLong() ?: 0L

    // Move the fishes to the correct group and add new fishes
    (0 until 256).forEach {
        val pop = t0
        t0 = t1
        t1 = t2
        t2 = t3
        t3 = t4
        t4 = t5
        t5 = t6
        t6 = t7 + pop //add those wo had a baby as well
        t7 = t8
        t8 = pop // all the new babies start at 8
    }

    val result = (t0 + t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8)
    println(result)

}

suspend fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = coroutineScope {
    map { async { f(it) } }.awaitAll()
}
