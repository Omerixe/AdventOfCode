object Day09 {

    fun start() {
        val input = readFile(9).split(",").map { it.toLong() }
        part1(input)
        part2(input)
    }

    fun part1(input: List<Long>) {
        IntcodeComputerV4(input).calculate(listOf(1))
    }

    fun part2(input: List<Long>) {
        IntcodeComputerV4(input).calculate(listOf(2))
    }

    fun test1() {
        val input1 = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99".split(",").map { it.toLong() }
        val input2 = "1102,34915192,34915192,7,4,7,99,0".split(",").map { it.toLong() }
        val input3 = "104,1125899906842624,99".split(",").map { it.toLong() }
        part1(input1)
        part1(input2)
        part1(input3)
    }


}

fun main(args: Array<String>) {
    Day09.start()
}