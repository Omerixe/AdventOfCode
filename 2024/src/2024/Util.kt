package `2024`

fun String.splitIntoPair(delimiter: String): Pair<String, String> =
    split(delimiter, limit = 2).chunked(2).map { Pair(it[0], it[1]) }.first()