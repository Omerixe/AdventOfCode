package `2021`

import java.io.File


fun main() {
    val input = File(ClassLoader.getSystemResource("2021/input10.txt").toURI()).readLines()
        .map { it.split("").filter { it.isNotEmpty() } }

    var errorCount = 0
    val autoCompletelist = mutableListOf<Long>()
    input.forEachIndexed outer@{ idx, line ->
        val stack = mutableListOf<String>()
        line.forEach { character ->
            if ("<{([".contains(character)) {
                stack.push(character)
            } else {
                val last = stack.pop()
                when (character) {
                    ")" -> if (last != "(") {
                        errorCount += 3
                        return@outer
                    }

                    "]" -> if (last != "[") {
                        errorCount += 57
                        return@outer
                    }

                    "}" -> if (last != "{") {
                        errorCount += 1197
                        return@outer
                    }

                    ">" -> if (last != "<") {
                        errorCount += 25137
                        return@outer
                    }
                }
            }
        }
        autoCompletelist.add(autoComplete(stack))
    }
    println("Part 1: $errorCount")
    println("Part 2: ${autoCompletelist.sorted().get(autoCompletelist.size / 2)}")

}

private fun autoComplete(stack: MutableList<String>): Long {
    var autoCompleteCount = 0L
    (0 until stack.size).forEach {
        when (stack.pop()) {
            "(" -> autoCompleteCount = (autoCompleteCount * 5) + 1
            "[" -> autoCompleteCount = (autoCompleteCount * 5) + 2
            "{" -> autoCompleteCount = (autoCompleteCount * 5) + 3
            "<" -> autoCompleteCount = (autoCompleteCount * 5) + 4
        }
    }
    return autoCompleteCount
}


fun <T> MutableList<T>.push(item: T) = this.add(this.count(), item)
fun <T> MutableList<T>.pop(): T? = if (this.count() > 0) this.removeAt(this.count() - 1) else null
