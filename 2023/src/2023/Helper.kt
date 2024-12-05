package `2023`

object Helper {
    /**
     * N1 N2 N3
     * N4 N0 N5
     * N6 N7 N8
     * Where N0 is the given starting point in the middle by x and y
     * Result: NorthWest = 0, North = 1, NorthEast = 2, West = 3, East = 4,
     * SouthWest = 5, South = 6, SouthEast = 7
     */
    fun getAllSurroundingElements(map: List<String>, x: Int, y: Int): List<Pair<Char?, Pair<Int, Int>>> {
        val realMap = map.map { it.toCharArray() }
        val result = mutableListOf<Pair<Char, Pair<Int, Int>>>()
        result.add(Pair(realMap[y - 1][x - 1], Pair(y - 1, x - 1))) //N1
        result.add(Pair(realMap[y - 1][x], Pair(y - 1, x))) //N2
        result.add(Pair(realMap[y - 1][x + 1], Pair(y - 1, x + 1))) //N3
        result.add(Pair(realMap[y][x - 1], Pair(y, x - 1))) //N4
        result.add(Pair(realMap[y][x + 1], Pair(y, x + 1))) //N5
        result.add(Pair(realMap[y + 1][x - 1], Pair(y + 1, x - 1))) //N6
        result.add(Pair(realMap[y + 1][x], Pair(y + 1, x))) //N7
        result.add(Pair(realMap[y + 1][x + 1], Pair(y + 1, x + 1))) //N8
        return result
    }

    /**
     *    N1
     * N2 N0 N3
     *    N4
     * Where N0 is the given starting point in the middle by x and y
     * Result: North = 0, West = 1, East = 2, South = 3
     */
    fun getStraightSurroundingElements(map: List<String>, x: Int, y: Int): List<Pair<Char, Pair<Int, Int>>> {
        val realMap = map.map { it.toCharArray() }
        val result = mutableListOf<Pair<Char, Pair<Int, Int>>>()
        result.add(Pair(realMap[y - 1][x], Pair(y - 1, x))) //N1
        result.add(Pair(realMap[y][x - 1], Pair(y, x - 1))) //N2
        result.add(Pair(realMap[y][x + 1], Pair(y, x + 1))) //N3
        result.add(Pair(realMap[y + 1][x], Pair(y + 1, x))) //N4
        return result
    }

    fun addPaddingTo2DList(input: List<String>, paddingChar: Char): List<String> {
        val lineLenght = input.first().length
        val paddingLine = listOf(paddingChar.toString().repeat(lineLenght + 2))
        return paddingLine + input.map { ".$it." } + paddingLine
    }

    fun transpose(input: List<String>): List<String> {
        return (0 until input.first().length).map { x ->
            (input.indices).map { y ->
                input[y][x]
            }.joinToString("")
        }
    }
}