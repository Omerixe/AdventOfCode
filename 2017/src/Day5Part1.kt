fun main(args: Array<String>) {
    val input5 = readFile(5)
    val inputList = test5.split("\n")
    val mutableList = inputList.toMutableList()
    var nextIdx = 0
    var numOfJumps = 0
    while (nextIdx < inputList.size - 1) {
        var value = inputList.get(nextIdx).toInt()
        mutableList.set(nextIdx, (value + 1).toString())
        nextIdx = value
        numOfJumps++
    }
    println(numOfJumps)
}