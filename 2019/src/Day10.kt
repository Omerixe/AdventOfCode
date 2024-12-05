object Day10 {

    fun start() {
        val input = readFile(10).split("\n").map { it.toCharArray() }
        val mainPlanet = part1(input)
        part2(input, mainPlanet)
    }

    fun part1(input: List<CharArray>): Pair<Int, Int> {
        val results: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()
        for (yIdx in input.indices) {
            for (xIdx in input[yIdx].indices) {
                if (input[yIdx][xIdx] == '#') {
                    results[Pair(xIdx, yIdx)] = checkVisiblePlanets(xIdx, yIdx, input)
                }
            }
        }
        val max = results.maxBy { it.value }
        println(max)
        return max!!.key
    }

    private fun checkVisiblePlanets(xIdx: Int, yIdx: Int, input: List<CharArray>): Int {
        val gradient = mutableSetOf<Pair<Int, Int>>()
        for (y in input.indices) {
            for (x in input[y].indices) {
                if (x == xIdx && y == yIdx) continue
                if (input[y][x] == '#') {
                    val yDiff = (yIdx - y)
                    val xDiff = (xIdx - x)
                    val divisor = gcd(yDiff, xDiff)
                    if (divisor != 0) {
                        gradient.add(Pair(xDiff / divisor, yDiff / divisor))
                    } else {
                        gradient.add(Pair(xDiff, yDiff))
                    }
                }
            }
        }
        return gradient.size
    }

    fun part2(input: List<CharArray>, mainPlanet: Pair<Int, Int>) {
        val gradientMap = calculateGradient(input, mainPlanet)
        var count = 0
        while (gradientMap.isNotEmpty()) {
            val firstQuarter = gradientMap.toList()
                .filter { it.first.first >= mainPlanet.first && it.first.second <= mainPlanet.second }
            count = deletePlanets(firstQuarter, gradientMap, mainPlanet, count)

            val secondQuarter =
                gradientMap.toList().filter { it.first.first > mainPlanet.first && it.first.second > mainPlanet.second }
            count = deletePlanets(secondQuarter, gradientMap, mainPlanet, count)

            val thirdQuarter = gradientMap.toList()
                .filter { it.first.first <= mainPlanet.first && it.first.second >= mainPlanet.second }
            count = deletePlanets(thirdQuarter, gradientMap, mainPlanet, count)

            val fourthQuarter =
                gradientMap.toList().filter { it.first.first < mainPlanet.first && it.first.second < mainPlanet.second }
            count = deletePlanets(fourthQuarter, gradientMap, mainPlanet, count)
        }
        println("blub")
    }

    private fun deletePlanets(
        firstQuarter: List<Pair<Pair<Int, Int>, Float>>,
        gradientMap: MutableMap<Pair<Int, Int>, Float>,
        mainPlanet: Pair<Int, Int>,
        countBefore: Int
    ): Int {
        var count = countBefore
        val sortedList = firstQuarter
            .map { if (it.second.isInfinite()) Pair(it.first, -666f) else it }
            .sortedBy { (_, value) -> value }
        var i = 0
        while (i < sortedList.size) {
            val planet = sortedList[i]
            val sameGradient = sortedList.filter { it.second == planet.second }
            if (sameGradient.count() == 1) {
                gradientMap.remove(planet.first)
                count++
                println("$count: ${planet.first}")
                i++
            } else {
                val distances = sameGradient.map {
                    Pair(
                        it.first,
                        distanceBetweenTwoPoints(it.first.first, it.first.second, mainPlanet.first, mainPlanet.second)
                    )
                }
                val minDist = distances.minBy { it.second }!!
                gradientMap.remove(minDist.first)
                count++
                println("$count: ${minDist.first}")
                i += sameGradient.size
            }
        }
        return count
    }

    private fun calculateGradient(input: List<CharArray>, middle: Pair<Int, Int>): MutableMap<Pair<Int, Int>, Float> {
        val result: MutableMap<Pair<Int, Int>, Float> = mutableMapOf()
        for (yIdx in input.indices) {
            for (xIdx in input[yIdx].indices) {
                if (xIdx == middle.first && yIdx == middle.second) continue
                if (input[yIdx][xIdx] == '#') {
                    val xDiff = middle.first - xIdx
                    val yDiff = middle.second - yIdx
                    result[Pair(xIdx, yIdx)] = yDiff.toFloat() / xDiff
                }
            }
        }
        return result
    }

    fun test1() {
        val test1 = (".#..#\n" +
                ".....\n" +
                "#####\n" +
                "....#\n" +
                "...##").split("\n").map { it.toCharArray() }

        val test2 = ("......#.#.\n" +
                "#..#.#....\n" +
                "..#######.\n" +
                ".#.#.###..\n" +
                ".#..#.....\n" +
                "..#....#.#\n" +
                "#..#....#.\n" +
                ".##.#..###\n" +
                "##...#..#.\n" +
                ".#....####").split("\n").map { it.toCharArray() }

        val test3 = ("#.#...#.#.\n" +
                ".###....#.\n" +
                ".#....#...\n" +
                "##.#.#.#.#\n" +
                "....#.#.#.\n" +
                ".##..###.#\n" +
                "..#...##..\n" +
                "..##....##\n" +
                "......#...\n" +
                ".####.###.").split("\n").map { it.toCharArray() }

        val test4 = (".#..#..###\n" +
                "####.###.#\n" +
                "....###.#.\n" +
                "..###.##.#\n" +
                "##.##.#.#.\n" +
                "....###..#\n" +
                "..#.#..#.#\n" +
                "#..#.#.###\n" +
                ".##...##.#\n" +
                ".....#.#..").split("\n").map { it.toCharArray() }

        val test5 = (".#..##.###...#######\n" +
                "##.############..##.\n" +
                ".#.######.########.#\n" +
                ".###.#######.####.#.\n" +
                "#####.##.#.##.###.##\n" +
                "..#####..#.#########\n" +
                "####################\n" +
                "#.####....###.#.#.##\n" +
                "##.#################\n" +
                "#####.##.###..####..\n" +
                "..######..##.#######\n" +
                "####.##.####...##..#\n" +
                ".#####..#.######.###\n" +
                "##...#.##########...\n" +
                "#.##########.#######\n" +
                ".####.#.###.###.#.##\n" +
                "....##.##.###..#####\n" +
                ".#.#.###########.###\n" +
                "#.#.#.#####.####.###\n" +
                "###.##.####.##.#..##").split("\n").map { it.toCharArray() }
        part1(test1)
        part1(test2)
        part1(test3)
        part1(test4)
        part1(test5)
    }

    fun test2() {
        val input = (".#....#####...#..\n" +
                "##...##.#####..##\n" +
                "##...#...#.#####.\n" +
                "..#.....X...###..\n" +
                "..#.#.....#....##").split("\n").map { it.toCharArray() }

        val lastTest = (".#..##.###...#######\n" +
                "##.############..##.\n" +
                ".#.######.########.#\n" +
                ".###.#######.####.#.\n" +
                "#####.##.#.##.###.##\n" +
                "..#####..#.#########\n" +
                "####################\n" +
                "#.####....###.#.#.##\n" +
                "##.#################\n" +
                "#####.##.###..####..\n" +
                "..######..##.#######\n" +
                "####.##.####...##..#\n" +
                ".#####..#.######.###\n" +
                "##...#.##########...\n" +
                "#.##########.#######\n" +
                ".####.#.###.###.#.##\n" +
                "....##.##.###..#####\n" +
                ".#.#.###########.###\n" +
                "#.#.#.#####.####.###\n" +
                "###.##.####.##.#..##").split("\n").map { it.toCharArray() }

        println(part2(input, Pair(8, 3)))
        println(part2(lastTest, Pair(11, 13)))
    }
}

fun main(args: Array<String>) {
    Day10.start()
}