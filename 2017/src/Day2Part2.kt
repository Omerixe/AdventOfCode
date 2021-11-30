
val testInput2 = "5\t9\t2\t8\n" +
        "9\t4\t7\t3\n" +
        "3\t8\t6\t5"

fun main(args: Array<String>) {
    val rows = input2.split("\n")
    var sum = 0
    for (row in rows) {
        for (cell in row.split("\t")) {
            val cell1Double = cell.toDouble()
            for (cell2 in row.split("\t")) {
                if (cell.equals(cell2)) {
                    break;
                }
                val cell2Double = cell2.toDouble()
                var result = cell1Double / cell2Double
                if (Math.ceil(result).equals(result)) {
                    sum += result.toInt()
                }
                result = cell2Double / cell1Double
                if (Math.ceil(result).equals(result)) {
                    sum += result.toInt()
                }
            }
        }
    }
    println(sum)
}