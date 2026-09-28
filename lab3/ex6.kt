package lab3

fun main() {
    print("Введіть рядок: ")
    val text = readln()

    // Спосіб 1: вручну через Map
    val counts = mutableMapOf<Char, Int>()
    text.forEach { ch ->
        counts[ch] = (counts[ch] ?: 0) + 1
    }
    counts.forEach { (ch, n) -> println("'$ch' -> $n") }

    // Спосіб 2: вбудованими функціями
    println(text.groupingBy { it }.eachCount())
}