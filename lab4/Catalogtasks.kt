package lab4

data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val price: Double,
    val rating: Double,
    val inStock: Boolean
)

val products = listOf(
    Product(1, "Google Pixel 9", "Smartphones", 899.0, 4.8, true),
    Product(2, "iPhone 16 Pro", "Smartphones", 1199.0, 4.9, true),
    Product(3, "Galaxy A55", "Smartphones", 399.0, 4.3, false),
    Product(4, "MacBook Air M3", "Laptops", 1299.0, 4.9, true),
    Product(5, "ThinkPad X1 Carbon", "Laptops", 1499.0, 4.6, false),
    Product(6, "Dell XPS 13", "Laptops", 1150.0, 4.4, true),
    Product(7, "Sony WH-1000XM5", "Audio", 349.0, 4.7, true),
    Product(8, "AirPods Pro 2", "Audio", 249.0, 4.8, false),
    Product(9, "Pixel Buds Pro 2", "Audio", 229.0, 4.5, true)
)

// Частина 4: власна функція вищого порядку (функція-розширення)
fun List<Product>.filterAndTransform(
    predicate: (Product) -> Boolean,
    transform: (Product) -> String
): List<String> {
    val result = mutableListOf<String>()
    for (product in this) {
        if (predicate(product)) {
            result.add(transform(product))
        }
    }
    return result
}

fun main() {
    println("===== Частина 1: ланцюжок трансформацій =====")
    products
        .filter { it.inStock && it.rating >= 4.7 && it.price < 1000.0 }
        .sortedByDescending { it.rating }
        .map { "Назва: ${it.name} | Рейтинг: ${it.rating} | Ціна: \$${it.price}" }
        .forEach { println(it) }

    println("\n===== Частина 2: пошук і перевірки =====")
    val expensiveLaptop = products.find { it.category == "Laptops" && it.price > 1200.0 }
    println("Перший ноутбук дорожчий за 1200: ${expensiveLaptop?.name ?: "Товар не знайдено"}")

    val hasExpensiveAudio = products.any { it.category == "Audio" && it.price > 300.0 }
    println("Є аудіо дорожче за 300: $hasExpensiveAudio")

    val allSmartphonesGood = products
        .filter { it.category == "Smartphones" }
        .all { it.rating > 4.0 }
    println("Усі смартфони мають рейтинг > 4.0: $allSmartphonesGood")

    println("\n===== Частина 3: розділення та групування =====")
    val (available, outOfStock) = products.partition { it.inStock }
    println("В наявності: ${available.size}")
    println("Немає на складі: ${outOfStock.size}")

    val byCategory: Map<String, List<Product>> = products.groupBy { it.category }

    byCategory.forEach { (category, items) ->
        val top = items.maxByOrNull { it.price }
        if (top != null) {
            println("Категорія $category -> Найдорожчий: ${top.name} (\$${top.price})")
        }
    }

    println("\n===== Частина 4: filterAndTransform =====")
    val promo = products.filterAndTransform({ it.price < 300.0 }) {
        "Акційна ціна на ${it.name}: лише \$${it.price}!"
    }
    promo.forEach { println(it) }

    println("\n===== ⭐ fold / reduce =====")
    val inStock = products.filter { it.inStock }
    val total = inStock.fold(0.0) { acc, product -> acc + product.price }
    val average = total / inStock.size
    println("Сумарна вартість товарів у наявності: \$$total")
    println("Середня вартість: \$${String.format(java.util.Locale.US, "%.2f", average)}")
}