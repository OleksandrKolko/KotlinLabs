package lab5

// ===== Вправа 1: безпечні виклики та let =====
data class ProductInfo(val priceInDollars: Double?)

class Product {
    fun getProductInfo(): ProductInfo? {
        return ProductInfo(100.0)
    }
}

// Було: кілька перевірок на null через if. Стало: один вираз
fun Product.getPriceInEuros() = getProductInfo()?.priceInDollars?.let { convertToEuros(it) }

fun convertToEuros(dollars: Double): Double {
    return dollars * 0.85
}

// ===== Вправа 2: ланцюжок apply + also =====
data class User(val id: Int, var email: String)

fun updateEmail(user: User, newEmail: String): User = user
    .apply { email = newEmail }
    .also { println("Updating email for user with ID: ${it.id}") }

fun main() {
    println("===== Вправа 1 =====")
    val product = Product()
    val priceInEuros = product.getPriceInEuros()
    if (priceInEuros != null) {
        println("Price in Euros: €$priceInEuros")
    } else {
        println("Price information is not available.")
    }

    println("\n===== Вправа 2 =====")
    val user = User(1, "old_email@example.com")
    val updatedUser = updateEmail(user, "new_email@example.com")
    println("Updated User: $updatedUser")
}