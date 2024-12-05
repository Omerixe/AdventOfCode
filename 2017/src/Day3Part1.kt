fun main(args: Array<String>) {
    val input3 = readFile(3).first().toInt()
    var round = Math.ceil(Math.sqrt(input3.toDouble()))
    if (round / 2 == Math.floor(round / 2)) {
        round += 1
    }
    println(round)
    val idx = (round - 1) / 2
    println(idx)
    val rightBottom = round * round
    val leftTop = getLeftTop(idx.toInt())
    val diff = (rightBottom - leftTop) / 2

    var mitte = 0.0
    if (input3 <= leftTop && input3 <= leftTop - diff) {
        //rechts
        println("rechts")
        mitte = (leftTop - diff) - diff / 2
    } else if (input3 <= leftTop) {
        //oben
        println("oben")
        mitte = (leftTop) - diff / 2
    } else if (input3 <= leftTop + diff) {
        //links
        println("links")
        mitte = (leftTop + diff) - diff / 2
    } else {
        //unten
        println("unten")
        mitte = (leftTop + diff) + diff / 2
    }
    val wert = Math.abs(input3 - mitte)
    println(wert + idx)


}

fun getLeftTop(idx: Int): Int {
    var sum = 0
    for (i in 0..idx) {
        if (i == 0) {
            sum += 1
        } else if (i == 1) {
            sum += 4
        } else {
            sum += (i - 1) * 8 + 4
        }
    }
    return sum
}