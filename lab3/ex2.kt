package lab3

// Вправа 2: функція приймає число і дію (лямбду без параметрів)
fun repeatN(n: Int, action: () -> Unit) {
    for (i in 1..n) {
        action()
    }
}

fun main() {
    // Вправа 1: побудувати URL для кожної дії
    val actions = listOf("title", "year", "author")
    val prefix = "https://example.com/book-info"
    val id = 5
    val urls = actions.map { action -> "$prefix/$id/$action" }
    println(urls)

    // Вправа 2: надрукувати "Hello" 5 разів
    repeatN(5) { println("Hello") }
}