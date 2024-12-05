fun main(args: Array<String>) {
    val input4 = readFile(4)
    val inputs = input4.split("\n")
    var result = inputs.size
    println(result)
    for (line in inputs) {
        val set: MutableSet<String> = mutableSetOf()
        for (word in line.split(" ")) {
            if (!set.add(word)) {
                result--
                break;
            }
        }
    }
    println(result)
}