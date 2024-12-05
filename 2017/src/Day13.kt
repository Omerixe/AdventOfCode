object Day13 {
    val input = readFile(13).split("\n")

    fun start() {
        part1()
        part2()
    }

    fun part1() {
        val firewalls = getInitialFirewalls()
        var severity = 0
        for (i in 0..firewalls.keys.last()) {
            val firewall = firewalls.get(i)

            if (firewall != null && firewall.position == 0) {
                severity += firewall.depth * firewall.layer
            }
            firewalls.forEach { it.value.plusOne() }
        }
        println(severity)
    }

    private fun getInitialFirewalls() =
        input.map { it.split(": ") }
            .associateBy({ it.get(0).toInt() }, { Firewall(it.get(0).toInt(), it.get(1).toInt()) })

    fun part2() {
        var firewalls = getInitialFirewalls()
        var severity = Int.MAX_VALUE
        var delay = 10
        val startFirewalls = firewalls.toMutableMap()
        for (i in 0..9) {
            startFirewalls.forEach { it.value.plusOneReset() }
        }
        while (severity != 0) {
            startFirewalls.forEach { it.value.plusOneReset() }
            startFirewalls.forEach { it.value.reset() }
            severity = 0
            firewalls = startFirewalls.toMutableMap()
            for (i in 0..firewalls.keys.last()) {
                val firewall = firewalls.get(i)

                if (firewall != null && firewall.position == 0) {
                    severity = 1
                    break
                }
                firewalls.forEach { it.value.plusOne() }
            }
            delay++
        }

        println(delay)
    }
}

fun main(args: Array<String>) {
    Day13.start()
}

class Firewall(var layer: Int, var depth: Int) {
    var resetValue = 0
    var resetPosition = false
    var position = 0
    var down = true

    fun plusOne() {
        if (down) {
            if (position == (depth - 1)) {
                down = false
                position--
            } else {
                position++
            }
        } else {
            if (position == 0) {
                down = true
                position++
            } else {
                position--
            }
        }
    }

    fun plusOneReset() {
        if (resetPosition) {
            if (resetValue == (depth - 1)) {
                resetPosition = false
                resetValue--
            } else {
                resetValue++
            }
        } else {
            if (resetValue == 0) {
                resetPosition = true
                resetValue++
            } else {
                resetValue--
            }
        }
    }

    fun reset() {
        position = resetValue
        down = resetPosition
    }
}