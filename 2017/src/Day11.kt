/**
 * Solution based on this link:
 * http://keekerdc.com/2011/03/hexagon-grids-coordinate-systems-and-distance-calculations/
 */
object Day11 {

    fun start() {
        val input = readFile(11)
        val directions = input.split(",")
        var y = 0
        var x = 0
        var max = 0
        for (direction in directions) {
            when (direction) {
                "n" -> y++
                "ne" -> {
                    y++
                    x--
                }

                "se" -> x--
                "s" -> y--
                "sw" -> {
                    y--
                    x++
                }

                "nw" -> x++
            }
            val z = 0 - x - y
            val tempMax = (listOf(Math.abs(x), Math.abs(y), Math.abs(z)).max()) ?: 0
            if (tempMax > max) {
                max = tempMax
            }
        }
        val z = 0 - x - y
        println((listOf(Math.abs(x), Math.abs(y), Math.abs(z)).max()))
        println(max)
    }
}

fun main(args: Array<String>) {
    Day11.start()
}