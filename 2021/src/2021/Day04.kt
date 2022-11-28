package `2021`

import java.io.File

fun main() {
    val dummyInput = """
        7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

        22 13 17 11  0
         8  2 23  4 24
        21  9 14 16  7
         6 10  3 18  5
         1 12 20 15 19
        
         3 15  0  2 22
         9 18 13 17  5
        19  8  7 25 23
        20 11 10 24  4
        14 21 16 12  6
        
        14 21 17 24  4
        10 16 15  9 19
        18  8 23 26 20
        22 11 13  6  5
         2  0 12  3  7
    """.trimIndent()

    /*val input = dummyInput.split("\n")*/
    val input = File(ClassLoader.getSystemResource("2021/input04.txt").toURI()).readLines()

    val draw = input.first().split(",")

    val boards = input
        .subList(1, input.size)
        .filter { it.isNotEmpty() }
        .map { it.split(" ").filter { it.isNotEmpty() } }
        .chunked(5)

    part1(draw, boards)

    part2(draw, boards)
}

private fun part2(
    draw: List<String>,
    boards: List<List<List<String>>>
) {
    var loserboard = listOf<List<String>>()
    draw.forEachIndexed { index, element ->
        val allDraws = draw.subList(0, index + 1)

        if (loserboard.isEmpty()) {
            val winnerBoards = boards.filter { boardWins(it, allDraws) }
            if (winnerBoards.size == boards.size - 1) {
                loserboard = boards.filter { !boardWins(it, allDraws) }.first()
            }
        } else {
            if (boardWins(loserboard, allDraws)) {
                calculateResult(loserboard, element, allDraws)
                return
            }
        }
    }
}


private fun part1(
    draw: List<String>,
    boards: List<List<List<String>>>
) {
    draw.forEachIndexed { index, element ->
        val allDraws = draw.subList(0, index + 1)
        boards.forEach { board ->
            if (boardWins(board, allDraws)) {
                calculateResult(board, element, allDraws)
                return
            }
        }
    }
}

private fun calculateResult(
    loserboard: List<List<String>>,
    element: String,
    allDraws: List<String>
) {
    println("Found correct board: $loserboard with draw: $element")
    val sum = loserboard.map {
        it.filter { !allDraws.contains(it) }.sumOf { it.toInt() }
    }.sum()
    println("Sum: $sum, draw: $element, product: ${sum * element.toInt()}")
    return
}

private fun boardWins(
    board: List<List<String>>,
    allDraws: List<String>
): Boolean {
    board.map {
        if (allDraws.containsAll(it)) {
            return true
        }
        (0 until board.first().size).map { index ->
            board.map { it[index] }
        }.map {
            if (allDraws.containsAll(it)) {
                return true
            }
        }
    }
    return false
}