object Day10 {

    val input = readFile(10)
    fun start() {
        part1()
        part2()
    }

    fun part1() {
        val hash = listOf(0..255).flatten().toMutableList()
        val inputLength = input.split(",").map { it.toInt() }
        doOneRound(hash, inputLength, 0, 0)
        println(hash.get(0) * hash.get(1))
    }

    fun part2() {
        val inputLength = input.toCharArray().map { it.toInt() }.toMutableList()
        val denseHash = makeKnotHash(inputLength)
        println(denseHash.map {
            if (it.toString(16).length < 2) {
                "0" + it.toString(16)
            } else {
                it.toString(16)
            }
        }.joinToString(""))
    }

    private fun doOneRound(hash: MutableList<Int>, inputLength: List<Int>, idx: Int, skipSize: Int): Pair<Int, Int> {
        var idx1 = idx
        var skipSize1 = skipSize
        for (length in inputLength) {
            val area = mutableListOf<Pair<Int, Int>>()

            for (i in idx1..(idx1 + length) - 1) {
                if (i < hash.size) {
                    area.add(Pair(i, hash.get(i)))
                } else {
                    area.add(Pair(i % hash.size, hash.get(i % hash.size)))
                }
            }
            val reversedArea = area.reversed()
            reversedArea.forEachIndexed { index, value ->
                hash.set(area.get(index).first, value.second)
            }
            idx1 += (skipSize1 + length)
            idx1 = idx1 % hash.size
            skipSize1++
        }
        return Pair(idx1, skipSize1)
    }

    fun makeKnotHash(inputLengthP: List<Int>): MutableList<Int> {
        val hash = listOf(0..255).flatten().toMutableList()
        val inputLength: MutableList<Int> = inputLengthP.toMutableList()
        inputLength.addAll(mutableListOf(17, 31, 73, 47, 23))
        var idx = 0
        var skipSize = 0
        for (i in 0..63) {
            val result = doOneRound(hash, inputLength, idx, skipSize)
            idx = result.first
            skipSize = result.second
        }
        val denseHash = mutableListOf<Int>()
        var index = 0
        for (i in 0..15) {
            var res = 0
            for (j in 0..15) {
                res = res xor hash.get(index)
                index++
            }
            denseHash.add(res)
        }

        return denseHash
    }
}

fun main(args: Array<String>) {
    Day10.start()
}
