package `2023`

import java.io.File

fun main() {
    val dummyInput = """
Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    """.trimIndent()

    //val inputs = dummyInput.split("\n")

    val inputs = File(ClassLoader.getSystemResource("2023/input02.txt").toURI()).readLines()
    part1(inputs)
    part2(inputs)
}

fun part1(inputs: List<String>) {
    val maxRed = 12
    val maxGreen = 13
    val maxBlue = 14

    var sum = 0

    inputs.forEach line@{ line ->
        // First the game number and the rest
        val gameRegex = Regex("""Game (\d+): (.+)$""")
        val gameMatchResult = gameRegex.find(line)
        val gameNo = gameMatchResult?.groups?.get(1)?.value?.toInt()
        // Then split the rest with ;
        val cubeSubset = gameMatchResult?.groups?.get(2)?.value?.split(";")
        cubeSubset?.forEach { subset ->
            // Then extract blue, red, green numbers
            val blueRegex = Regex("""(\d+) blue""")
            val blueNo = blueRegex.find(subset)?.groups?.get(1)?.value?.toInt()
            val greenRegex = Regex("""(\d+) green""")
            val greenNo = greenRegex.find(subset)?.groups?.get(1)?.value?.toInt()
            val redRegex = Regex("""(\d+) red""")
            val redNo = redRegex.find(subset)?.groups?.get(1)?.value?.toInt()

            if ((redNo == null || maxRed >= redNo) &&
                (greenNo == null || maxGreen >= greenNo) &&
                (blueNo == null || maxBlue >= blueNo)
            ) {
            } else {
                return@line
            }
        }
        sum += gameNo!!
    }
    println(sum)

}

fun part2(inputs: List<String>) {

    var sum = 0

    inputs.forEach line@{ line ->
        var minGreen = 0
        var minRed = 0
        var minBlue = 0

        // First the game number and the rest
        val gameRegex = Regex("""Game (\d+): (.+)$""")
        val gameMatchResult = gameRegex.find(line)
        val gameNo = gameMatchResult?.groups?.get(1)?.value?.toInt()
        // Then split the rest with ;
        val cubeSubset = gameMatchResult?.groups?.get(2)?.value?.split(";")
        cubeSubset?.forEach { subset ->
            // Then extract blue, red, green numbers
            val blueRegex = Regex("""(\d+) blue""")
            val blueNo = blueRegex.find(subset)?.groups?.get(1)?.value?.toInt()
            val greenRegex = Regex("""(\d+) green""")
            val greenNo = greenRegex.find(subset)?.groups?.get(1)?.value?.toInt()
            val redRegex = Regex("""(\d+) red""")
            val redNo = redRegex.find(subset)?.groups?.get(1)?.value?.toInt()

            if (redNo != null && redNo > minRed) minRed = redNo
            if (blueNo != null && blueNo > minBlue) minBlue = blueNo
            if (greenNo != null && greenNo > minGreen) minGreen = greenNo
        }
        sum += (minRed * minBlue * minGreen)
    }
    println(sum)
}