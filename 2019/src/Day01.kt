object Day01 {

    fun start() {
        val input = readFile(1)
        part1(input.split("\n"))
        part2(input.split("\n"))
    }

    fun part1(input: List<String>) {
        println(input.map { it.toInt() / 3 - 2 }.sum())
    }

    fun part2(input: List<String>) {
        var list = input.map { it.toInt() }
        var sum = 0

        for (number in list) {
            var tmp = number
            while (tmp > 2) {
                tmp = tmp / 3 - 2
                if (tmp < 0) {
                    continue
                } else {
                    sum += tmp
                }
            }
        }
        println(sum)
    }

    fun test1() {
        part1(listOf("12"))
        part1(listOf("14"))
        part1(listOf("1969"))
        part1(listOf("100756"))
    }

    fun test2() {
        part2(listOf("12"))
        part2(listOf("14"))
        part2(listOf("1969"))
        part2(listOf("100756"))
        part2(listOf("12", "14", "1969", "100756")) //2 + 2 + 966 + 50346 = 51316
    }


}

fun main(args: Array<String>) {
    //Day01.test1()
    //Day01.test2()
    Day01.start()
}