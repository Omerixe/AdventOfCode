object Day04 {
    fun start() {
        val input = readFile(4).split("-").map { it.toInt() }
        part1(input[0], input[1])
        part2(input[0], input[1])
    }

    fun part1(from: Int, to: Int) {
        val result: MutableList<Int> = mutableListOf()
        for (pw in from..to) {
            if (checkValid(pw)) {
                result.add(pw)
            }
        }
        println(result.size)
    }

    fun checkValid(pw: Int): Boolean {
        val stringPw = pw.toString()
        var lastOne = '0'
        var doubleFound = false
        for (char in stringPw) {
            if (char.toInt() >= lastOne.toInt()) {
                if (char.equals(lastOne)) {
                    doubleFound = true
                }
            } else {
                return false
            }
            lastOne = char
        }
        return doubleFound
    }

    fun checkValid2(pw: Int): Boolean {
        val stringPw = pw.toString()
        var lastOne = '0'
        var sameCount = 0
        var doubleFound = false
        for (char in stringPw) {
            if (char.toInt() >= lastOne.toInt()) {
                if (char.equals(lastOne)) {
                    sameCount++
                } else {
                    if (sameCount == 1) {
                        doubleFound = true
                    }
                    sameCount = 0
                }
            } else {
                return false
            }
            lastOne = char
        }
        if (sameCount == 1) {
            doubleFound = true
        }
        return doubleFound
    }


    fun part2(from: Int, to: Int) {
        val result: MutableList<Int> = mutableListOf()
        for (pw in from..to) {
            if (checkValid2(pw)) {
                result.add(pw)
            }
        }
        println(result.size)
    }

    fun test1() {
        println(checkValid(111111))
        println(checkValid(223450))
        println(checkValid(123789))
    }

    fun test2() {
        println(checkValid2(112233))
        println(checkValid2(123444))
        println(checkValid2(111122))
    }
}


fun main(args: Array<String>) {
    Day04.start()
}