package lab3

fun stringFilter(strings: List<String>, predicate: (String) -> Boolean): List<String> {
    val result = mutableListOf<String>()
    strings.forEach {
        if (predicate(it)) result.add(it)
    }
    return result
}

fun main() {
    val words = listOf("hello", "abc123", "kotlin2", "world", "42")

    println(stringFilter(words) { str -> str.any { it.isDigit() } })  // [abc123, kotlin2, 42]
    println(stringFilter(words) { it.startsWith("w") })               // [world]
}