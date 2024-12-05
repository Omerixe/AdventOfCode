fun main(args: Array<String>) {
    val input = readFile(1)
    var sum = 0;
    var last = '0'
    for (value in input.toCharArray()) {
        if (last.equals(value)) {
            sum += value.toString().toInt()
        }
        last = value;
    }
    if (INPUT[0].equals(INPUT[INPUT.length - 1])) {
        sum += INPUT[0].toString().toInt()
    }
    println(sum)

}