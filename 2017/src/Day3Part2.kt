fun main(args: Array<String>) {
    val input3 = readFile(3).first().toInt()
    val map: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()
    var found = false
    var m = 0
    var n = 0
    var idx = 0
    while (!found) {
        if (idx == 0) {
            addToMap(n, m, map);
        } else if (idx == 1) {
            //Momentan gehen wir zu weit hoch, hier nochmal schauen.
            m++
            found = addToMap(n, m, map)

            n++
            found = addToMap(n, m, map)

            m--
            found = addToMap(n, m, map)

            m--
            found = addToMap(n, m, map)

            n--
            found = addToMap(n, m, map)

            n--
            found = addToMap(n, m, map)
            m++
            found = addToMap(n, m, map)

            m++
            found = addToMap(n, m, map)

        } else {
            //rechts (immer 1)
            m++
            found = addToMap(n, m, map)

            val length = idx * 2 - 1 // Ohne -1, da wir immer das erste in der Zeile schon füllen
            //hoch
            for (i in n..(n + length - 1)) {
                n++
                found = addToMap(n, m, map)
            }
            //links
            for (i in m downTo (m - length)) {
                m--
                found = addToMap(n, m, map)
            }
            //runter
            for (i in n downTo (n - length)) {
                n--
                found = addToMap(n, m, map)
            }
            //rechts
            for (i in m..(n + length)) {
                m++
                found = addToMap(n, m, map)
            }
        }

        idx++
    }
}

private fun addToMap(n: Int, m: Int, map: MutableMap<Pair<Int, Int>, Int>): Boolean {
    val pair = Pair(n, m)
    if (n == 0 && m == 0) {
        map.put(pair, 1)
        return false
    }
    val calcValue = calculateValue(map, m, n)
    if (calcValue > input3) {
        println(calcValue)
        return true
    }
    map.put(pair, calcValue)
    return false
}

private fun calculateValue(map: MutableMap<Pair<Int, Int>, Int>, m: Int, n: Int): Int {
    var sum = 0
    var pair: Pair<Int, Int>
    //oben
    pair = Pair(n + 1, m)
    if (map.containsKey(pair)) {
        sum += map.get(pair)!!
    }
    //links oben
    pair = Pair(n + 1, m - 1)
    if (map.containsKey(pair)) {
        sum += map.get(pair)!!
    }
    //links
    pair = Pair(n, m - 1)
    if (map.containsKey(pair)) {
        sum += map.get(pair)!!
    }
    //links unten
    pair = Pair(n - 1, m - 1)
    if (map.containsKey(pair)) {
        sum += map.get(pair)!!
    }
    //unten
    pair = Pair(n - 1, m)
    if (map.containsKey(pair)) {
        sum += map.get(pair)!!
    }
    //rechts unten
    pair = Pair(n - 1, m + 1)
    if (map.containsKey(pair)) {
        sum += map.get(pair)!!
    }
    //rechts
    pair = Pair(n, m + 1)
    if (map.containsKey(pair)) {
        sum += map.get(pair)!!
    }
    //rechts oben
    pair = Pair(n + 1, m + 1)
    if (map.containsKey(pair)) {
        sum += map.get(pair)!!
    }
    return sum;
}