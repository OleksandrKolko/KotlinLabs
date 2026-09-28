package lab3

fun countMatching(strings: List<String>, predicate: (String) -> Boolean): Int {
    var count = 0
    strings.forEach {
        if (predicate(it)) count++
    }
    return count
}

fun main() {
    val words = listOf("hello", "abc123", "kotlin2", "world", "42")

    val withDigit = countMatching(words) { str -> str.any { it.isDigit() } }
    println("Рядків з цифрою: $withDigit")        // 3

    val longOnes = countMatching(words) { it.length > 5 }
    println("Рядків довших за 5: $longOnes")      // 2
}