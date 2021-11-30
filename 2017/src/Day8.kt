fun main(args: Array<String>) {
    val registers: MutableMap<String, Int> = mutableMapOf()
    val input = readFile(8)
    val list = input.split("\n")
    var maxVal = 0
    list.forEach {
        val parts = it.split(" ")
        val valueA = registers.getOrDefault(parts[0], parts[0].toIntOrNull() ?: 0)
        val valueB = registers.getOrDefault(parts[2], parts[2].toIntOrNull() ?: 0)
        val valueC = registers.getOrDefault(parts[4], parts[4].toIntOrNull() ?: 0)
        val valueD = registers.getOrDefault(parts[6], parts[6].toIntOrNull() ?: 0)
        var check = false
        when (parts[5]) {
            "<" -> if (valueC < valueD) check = true
            ">" -> if (valueC > valueD) check = true
            "!=" -> if (valueC != valueD) check = true
            "<=" -> if (valueC <= valueD) check = true
            ">=" -> if (valueC >= valueD) check = true
            "==" -> if (valueC == valueD) check = true
        }
        if (check) {
            val res = if (parts[1] == "dec") valueA - valueB else valueA + valueB
            registers.put(parts[0], res)
            //Part 2
            if (maxVal < (registers.maxBy { it.value })?.value ?: 0) {
                maxVal = registers.maxBy { it.value }?.value ?: 0
            }
        }
    }
    println(registers.maxBy { it.value })
    println(maxVal)
}