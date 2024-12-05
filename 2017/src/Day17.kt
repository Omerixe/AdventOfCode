object Day17 {
    val input = readFile(17).first().toInt()
    fun start() {
        //part 1
        var buffer = CircularBuffer(0)
        val initialBuffer = buffer
        for (i in 1..2017) {
            for (j in 1..input) {
                buffer = buffer.next
            }
            val newBuffer = CircularBuffer(i)
            newBuffer.next = buffer.next
            buffer.next = newBuffer
            buffer = buffer.next
        }
        println(buffer.next.value)

        //part 2
        for (i in 2018..50000000) {
            for (j in 1..input) {
                buffer = buffer.next
            }
            val newBuffer = CircularBuffer(i)
            newBuffer.next = buffer.next
            buffer.next = newBuffer
            buffer = buffer.next
        }

        println(initialBuffer.next.value)
    }
}

fun main(args: Array<String>) {
    Day17.start()
}

class CircularBuffer(var value: Int) {
    var next: CircularBuffer = this
}