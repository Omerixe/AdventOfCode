import java.math.BigDecimal

object Day15 {
    val input = readFile(15)
    val originalA = BigDecimal(input[0].replace("Generator A starts with ", "").toInt())
    val originalB = BigDecimal(input[1].replace("Generator B starts with ", "").toInt())
    val multiplierA = BigDecimal(16807)
    val multiplierB = BigDecimal(48271)
    val divisor = BigDecimal(2147483647)


    fun start() {
        part1()
        part2()
    }

    fun part1() {
        var inputA = originalA
        var inputB = originalB
        var count = 0

        for (i in 0..39999999) {
            inputA = inputA.multiply(multiplierA).rem(divisor)
            inputB = inputB.multiply(multiplierB).rem(divisor)

            val aString = "0000000000000000" + inputA.toInt().toString(2)
            val bString = "0000000000000000" + inputB.toInt().toString(2)

            if (aString.substring(aString.length - 16).equals(bString.substring(bString.length - 16))) {
                count++
            }
        }
        println(count)
    }

    fun part2() {
        var inputA = originalA
        var inputB = originalB
        var count = 0

        for (i in 0..4999999) {
            inputA = inputA.multiply(multiplierA).rem(divisor)
            while (inputA.rem(BigDecimal(4)).toInt() != 0) {
                inputA = inputA.multiply(multiplierA).rem(divisor)
            }

            inputB = inputB.multiply(multiplierB).rem(divisor)
            while (inputB.rem(BigDecimal(8)).toInt() != 0) {
                inputB = inputB.multiply(multiplierB).rem(divisor)
            }

            val aString = "0000000000000000" + inputA.toInt().toString(2)
            val bString = "0000000000000000" + inputB.toInt().toString(2)

            if (aString.substring(aString.length - 16).equals(bString.substring(bString.length - 16))) {
                count++
            }
        }
        println(count)
    }
}

fun main(args: Array<String>) {
    Day15.start()
}