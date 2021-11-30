
fun main(args: Array<String>) {
    val first: Array<Int> = arrayOf(14, 0, 15, 12, 11, 11, 3, 5, 1, 6, 8, 4, 9, 1, 8, 4)
    val set: MutableSet<String> = mutableSetOf(first.joinToString(","))

    val n = first.size
    var notFound = true
    var numOf = 0
    while (notFound) {
        val maxValue = first.max()
        val idxOfMax = first.indexOf(maxValue)
        val plus = Math.ceil(maxValue?.div(n.toDouble())!!).toInt()
        first.set(idxOfMax, 0)
        var tempMax = maxValue
        for (i in (idxOfMax + 1)..(n - 1)) {
            if (tempMax - plus > 0) {
                first.set(i, first.get(i) + plus)
            } else if (tempMax > 0) {
                first.set(i, first.get(i) + tempMax)
            }
            tempMax -= plus
        }
        for (i in 0..idxOfMax) {
            if (tempMax - plus > 0) {
                first.set(i, first.get(i) + plus)
            } else if (tempMax > 0) {
                first.set(i, first.get(i) + tempMax)
            }
            tempMax -= plus
        }

        numOf++
        notFound = set.add(first.joinToString(","))
    }
    println(numOf)
}