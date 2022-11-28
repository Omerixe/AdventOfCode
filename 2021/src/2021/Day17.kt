package `2021`

import java.lang.Integer.max
import java.lang.Math.abs

fun main() {
    val dummyXMin = 20
    val dummyXMax = 30
    val dummyYMin = -10
    val dummyYMax = -5

    val inputXMin = 155
    val inputXMax = 215
    val inputYMin = -132
    val inputYMax = -72

    val xMin = inputXMin
    val xMax = inputXMax
    val yMin = inputYMin
    val yMax = inputYMax

    val startPoint = Pair(0, 0)
    var highestY = 0
    var successCount = 0

    // There might be better values to calculate all the velocities to try, but id did end, so all fine ;)
    val allTestVelocities = (1..xMax).map { x ->
        if ((1..x).sum() < xMin) {
            return@map listOf()
        }
        (yMin..abs(yMin)).map { y ->
            Pair(x, y)
        }
    }.flatten()
    allTestVelocities.forEach {
        var highestLoopY = 0
        var currentPoint = startPoint
        var xVelocity = it.first
        var yVelocity = it.second
        while (xVelocity != 0) {
            currentPoint = Pair(currentPoint.first + xVelocity, currentPoint.second + yVelocity)
            highestLoopY = max(currentPoint.second, highestLoopY)
            xVelocity -= if (xVelocity > 0) 1 else -1
            yVelocity -= 1

            if (currentPoint.first in xMin..xMax && currentPoint.second in yMin..yMax) {
                highestY = max(highestY, highestLoopY)
                successCount++
                return@forEach
            }
        }
        // Check if we even are within the horizontal landing area
        if (currentPoint.first in xMin..xMax) {
            while (currentPoint.second > yMax) { //-5
                currentPoint = Pair(currentPoint.first, currentPoint.second + yVelocity)
                highestLoopY = max(currentPoint.second, highestLoopY)
                yVelocity -= 1
            }
            if (currentPoint.second >= yMin) { //-10
                // First check if we did land in the landing area
                highestY = max(highestY, highestLoopY)
                successCount++
            }
        }
    }
    println("Part1: $highestY")
    println("Part 2: $successCount")
}