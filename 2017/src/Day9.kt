import java.util.*

object Day9 {
    val stack: Stack<Char> = Stack()
    var garbage = false
    var ignoreNext = false
    var counter = 0
    var garbageCounter = 0

    fun start() {
        val input = readFile(9)
        //val input = "<<<<>"
        for (character in input) {
            if (!ignoreNext) {
                if (!garbage) {
                    when (character) {
                        '{' -> {
                            stack.push(character)
                            counter += stack.size
                        }

                        '}' -> stack.pop()
                        '<' -> garbage = true
                        '!' -> ignoreNext = true
                    }
                } else {
                    when (character) {
                        '>' -> garbage = false
                        '!' -> ignoreNext = true
                        else -> garbageCounter++
                    }
                }
            } else {
                ignoreNext = false
            }
        }
        println(counter)
        println(garbageCounter)
    }
}

fun main(args: Array<String>) {
    Day9.start()
}