package `2023`

import java.io.File

fun main() {

    //val inputs = File(ClassLoader.getSystemResource("2023/dummy10.txt").toURI()).readLines()
    val inputs = File(ClassLoader.getSystemResource("2023/input10.txt").toURI()).readLines()
    Day10.part1(inputs)
    Day10.part2(inputs)
}

object Day10 {
    private enum class Direction {
        North, East, South, West
    }

    private val startPoint = 'S'
    private val northPossibilities = listOf('7', 'F', '|')
    private val eastPossibilities = listOf('7', 'J', '-')
    private val southPossibilities = listOf('L', 'J', '|')
    private val westPossibilities = listOf('L', 'F', '-')

    /**
     * There are two possible directions as it is a loop
     * We can follow both and stop with each as soon as the stepcount goes down again
     * Do we really need to follow both? -> yes we do to find out where they meet, also halves the time
     */
    fun part1(inputs: List<String>) {
        val paddedInput = Helper.addPaddingTo2DList(inputs, '.')
        val startingPointY = paddedInput.indexOfFirst { it.contains(startPoint) }
        val startingPointX = paddedInput[startingPointY].indexOf(startPoint)

        //North = 0, West = 1, East = 2, South = 3
        val surroundingElements = Helper.getStraightSurroundingElements(paddedInput, startingPointX, startingPointY)
        val startDirection = surroundingElements.mapIndexed { index, element ->
            findDirectionFromInitial(index, element.first)
        }.filterNotNull().first()

        val loop = mutableListOf(Pair(startingPointY, startingPointX))
        var currentDirection = startDirection
        while (loop.size == 1 || paddedInput[loop.last().first][loop.last().second] != startPoint) {
            val nextPosition = nextPosition(currentDirection, loop.last())
            val nextSymbol = paddedInput[nextPosition.first][nextPosition.second]
            val nextDirection = nextDirection(nextSymbol, currentDirection)
            currentDirection = nextDirection
            loop.add(nextPosition)
        }
        println((loop.size - 1) / 2)
    }

    private fun findDirectionFromInitial(index: Int, symbol: Char): Direction? {
        return if (index == 0) {
            if (northPossibilities.contains(symbol)) {
                initialDirection(index)
            } else {
                null
            }
        } else if (index == 1) {
            if (westPossibilities.contains(symbol)) {
                initialDirection(index)
            } else {
                null
            }
        } else if (index == 2) {
            if (eastPossibilities.contains(symbol)) {
                initialDirection(index)
            } else {
                null
            }
        } else {
            if (southPossibilities.contains(symbol)) {
                initialDirection(index)
            } else {
                null
            }
        }
    }

    private fun initialDirection(index: Int): Direction {
        return when (index) {
            0 -> Direction.North
            1 -> Direction.West
            2 -> Direction.East
            3 -> Direction.South
            else -> throw IllegalStateException("Nope")
        }
    }

    private fun nextDirection(currentSymbol: Char, direction: Direction): Direction {
        return when (currentSymbol) {
            '|', '-' -> {
                direction
            }

            'L' -> {
                if (direction == Direction.South) {
                    Direction.East
                } else { //Should only be west
                    Direction.North
                }
            }

            'F' -> {
                if (direction == Direction.North) {
                    Direction.East
                } else { //Should only be west
                    Direction.South
                }
            }

            'J' -> {
                if (direction == Direction.South) {
                    Direction.West
                } else { //Should only be east
                    Direction.North
                }
            }

            '7' -> {
                if (direction == Direction.North) {
                    Direction.West
                } else { //Should only be east
                    Direction.South
                }
            }

            else -> direction
        }
    }

    private fun nextPosition(direction: Direction, currentPosition: Pair<Int, Int>): Pair<Int, Int> {
        return when (direction) {
            Direction.North -> Pair(currentPosition.first - 1, currentPosition.second)
            Direction.East -> Pair(currentPosition.first, currentPosition.second + 1)
            Direction.South -> Pair(currentPosition.first + 1, currentPosition.second)
            Direction.West -> Pair(currentPosition.first, currentPosition.second - 1)
        }
    }

    // 2 - wrong
    // 484 - too low
    // Loop through every step on the path and always consider the left element (assuming we go counter-clockwise)
    // Had to search in Reddit for that idea :(
    fun part2(inputs: List<String>) {
        val paddedInput = Helper.addPaddingTo2DList(inputs, '.')
        val startingPointY = paddedInput.indexOfFirst { it.contains(startPoint) }
        val startingPointX = paddedInput[startingPointY].indexOf(startPoint)

        //North = 0, West = 1, East = 2, South = 3
        val surroundingElements = Helper.getStraightSurroundingElements(paddedInput, startingPointX, startingPointY)
        val startDirection = surroundingElements.mapIndexed { index, element ->
            findDirectionFromInitial(index, element.first)
        }.filterNotNull().last()

        val loop = mutableListOf(Pair(startDirection, Pair(startingPointY, startingPointX)))
        while (loop.size == 1 || paddedInput[loop.last().second.first][loop.last().second.second] != startPoint) {
            val nextPosition = nextPosition(loop.last().first, loop.last().second)
            val nextSymbol = paddedInput[nextPosition.first][nextPosition.second]
            val nextDirection = nextDirection(nextSymbol, loop.last().first)
            loop.add(Pair(nextDirection, nextPosition))
        }

        val loopPoints = loop.map { it.second }
        val inside = loop.flatMap { (direction, position) ->
            //take left
            val symbol = paddedInput[position.first][position.second]
            when (symbol) {
                '-' -> {
                    if (direction == Direction.East) {
                        // Take the one north
                        listOf(takeNorth(position, loopPoints))
                    } else {
                        listOf(takeSouth(position, paddedInput.size, loopPoints))
                    }
                }

                '|' -> {
                    if (direction == Direction.North) {
                        listOf(takeWest(position, loopPoints))
                    } else {
                        listOf(takeEast(position, paddedInput.size, loopPoints))
                    }
                }

                '7' -> {
                    if (direction == Direction.South) {
                        listOf(
                            takeNorth(position, loopPoints),
                            takeEast(position, paddedInput.first().length, loopPoints)
                        )
                    } else {
                        listOf(takeSouth(position, paddedInput.size, loopPoints), takeWest(position, loopPoints))
                    }
                }

                'J' -> {
                    if (direction == Direction.West) {
                        listOf(
                            takeEast(position, paddedInput.first().length, loopPoints),
                            takeSouth(position, paddedInput.size, loopPoints)
                        )
                    } else {
                        listOf(takeWest(position, loopPoints), takeNorth(position, loopPoints))
                    }
                }

                'L' -> {
                    if (direction == Direction.North) {
                        listOf(takeSouth(position, paddedInput.size, loopPoints), takeWest(position, loopPoints))
                    } else {
                        listOf(
                            takeNorth(position, loopPoints),
                            takeEast(position, paddedInput.first().length, loopPoints)
                        )
                    }
                }

                else -> {
                    emptyList()
                }
            }
        }

        val insideSet = inside.filterNotNull().toMutableSet()
        var countBefore = inside.size
        var countAfter = 0
        while (countBefore != countAfter) {
            countBefore = countAfter
            val newList = insideSet.flatMap { position ->
                Helper.getAllSurroundingElements(paddedInput, position.second, position.first)
                    .filter { it.first != null }.map { it.second }.filter { !loopPoints.contains(it) }
            }.toSet()
            insideSet.addAll(newList)
            countAfter = insideSet.size
        }
        println(insideSet.size)
    }

    private fun takeEast(
        position: Pair<Int, Int>,
        maxLenght: Int,
        loopValues: List<Pair<Int, Int>>
    ) = if (position.second + 1 == maxLenght) null
    else {
        if (!loopValues.contains(Pair(position.first, position.second + 1))) {
            Pair(position.first, position.second + 1)
        } else null
    }

    private fun takeWest(
        position: Pair<Int, Int>,
        loopValues: List<Pair<Int, Int>>
    ) = if (position.second - 1 < 0) null
    else {
        if (!loopValues.contains(Pair(position.first, position.second - 1))) {
            Pair(position.first, position.second - 1)
        } else null
    }

    private fun takeSouth(
        position: Pair<Int, Int>,
        maxLenght: Int,
        loopValues: List<Pair<Int, Int>>
    ) = if (position.first + 1 == maxLenght) null
    else {
        // Take the one south
        if (!loopValues.contains(Pair(position.first + 1, position.second))) {
            Pair(position.first + 1, position.second)
        } else null
    }

    private fun takeNorth(
        position: Pair<Int, Int>,
        loopValues: List<Pair<Int, Int>>
    ) = if (position.first - 1 < 0) null
    else {
        if (!loopValues.contains(Pair(position.first - 1, position.second))) {
            Pair(position.first - 1, position.second)
        } else null
    }

}