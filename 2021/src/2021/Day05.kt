package `2021`

import java.io.File
import kotlin.math.abs

//Todo: Another option might be to "draw" each line by calculating its points and then just compare which point was
//"drawn" how often. My current solution calculates each point in the grid and then checks for each of them of how many
// lines it is a part. It's a bit slow as I have to go through every line x times (x = amount of points in the grid)
fun main() {

    val input = File(ClassLoader.getSystemResource("2021/input05.txt").toURI()).readLines()

    val ventLines = input.map { line ->
        line.split(" -> ").map { it.split(",").map(String::toInt).zipWithNext().first() }.zipWithNext().first()
    }

    //Only horizontal & vertical lines
    val relevantLines = ventLines.filter { (from, to) ->
        from.first == to.first || from.second == to.second
    }
    val result1 = calculateOverlappingPoints(relevantLines)
    println("Result Pt1: $result1")

    val result2 = calculateOverlappingPoints(ventLines)
    println("Result Pt2: $result2")
}

private fun calculateOverlappingPoints(relevantLines: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Int {
    var maxX = 0
    var maxY = 0
    relevantLines.forEach { points ->
        maxX = if (points.first.first > maxX) points.first.first else maxX
        maxX = if (points.second.first > maxX) points.second.first else maxX
        maxY = if (points.first.second > maxY) points.first.second else maxY
        maxY = if (points.second.second > maxY) points.second.second else maxY
    }

    val result = (0 until maxX + 1).map { x ->
        (0 until maxY + 1).map { y ->
            Pair(x, y)
        }
    }.flatten().map { (x, y) ->
        relevantLines.map inner@{ (point1, point2) ->
            pointIsOnLine(x, y, point1, point2)
        }
    }.filter { point -> point.count { it } >= 2 }.size
    return result
}

private fun pointIsOnLine(
    x: Int,
    y: Int,
    linePoint1: Pair<Int, Int>,
    linePoint2: Pair<Int, Int>
): Boolean {
    val dxc = x - linePoint1.first
    val dyc = y - linePoint1.second
    val dxl = linePoint2.first - linePoint1.first
    val dyl = linePoint2.second - linePoint1.second

    if ((dxc * dyl - dyc * dxl) != 0) return false

    if (abs(dxl) >= abs(dyl)) {
        if (dxl > 0) {
            return linePoint1.first <= x && x <= linePoint2.first
        } else {
            return linePoint2.first <= x && x <= linePoint1.first
        }
    } else {
        if (dyl > 0) {
            return linePoint1.second <= y && y <= linePoint2.second
        } else {
            return linePoint2.second <= y && y <= linePoint1.second
        }
    }
}