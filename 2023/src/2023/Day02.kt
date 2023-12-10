package `2023`

import java.io.File

fun main() {
    //val inputs = File(ClassLoader.getSystemResource("2023/dummy02.txt").toURI()).readLines()

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