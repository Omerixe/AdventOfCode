fun main(args: Array<String>) {
    val input2 = readFile(2)
    val rows = input2.split("\n")
    var sum = 0
    for (row in rows) {
        var highest = 0
        var lowest = Int.MAX_VALUE
        for (cell in row.split("\t")) {
            val cellInt = cell.toInt()
            if (cellInt > highest) {
                highest = cell.toInt()
            }
            if (cellInt < lowest) {
                lowest = cell.toInt()
            }
        }
        sum += (highest - lowest)
    }
    println(sum)
}