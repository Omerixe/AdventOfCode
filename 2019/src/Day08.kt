object Day08 {

    fun start() {
        val input = readFile(8)
        part1(input, 25, 6)
        part2(input, 25, 6)
    }

    fun part1(input: String, width: Int, height: Int) {
        //val groups = mutableListOf<List<String>>()
        var idx = 0
        var minZero = width * height
        var result = 0
        while (idx < input.length) {
            val substring = input.substring(idx, idx + (width * height))
            val zeroCount = substring.split("0").size - 1
            if (zeroCount < minZero) {
                minZero = zeroCount
                val oneCount = substring.split("1").size - 1
                val twoCount = substring.split("2").size - 1
                result = oneCount * twoCount
            }
            //groups.add(substring)
            idx += width * height
        }
        println(result)
    }

    fun part2(input: String, width: Int, height: Int) {
        val layerSize = width * height
        val layerGroups: MutableList<Char> = MutableList(layerSize) { '2' }
        for ((idx, char) in input.toCharArray().withIndex()) {
            val pixelIdx = idx % layerSize
            if (layerGroups[pixelIdx] != '0' && layerGroups[pixelIdx] != '1') {
                layerGroups[pixelIdx] = char
            }
            if (layerGroups.filter { it == '2' }.isEmpty()) {
                break
            }
        }
        for (i in 0 until layerSize step width) {
            println(layerGroups.subList(i, i + width).joinToString("").replace("1", "\u2591\u2591").replace("0", "  "))
        }
    }

    fun test1() {
        val input = "123456789012"
        part1(input, 3, 2)
    }

    fun test2() {
        val input = "0222112222120000"
        part2(input, 2, 2)
    }
}

fun main(args: Array<String>) {
    Day08.start()
}