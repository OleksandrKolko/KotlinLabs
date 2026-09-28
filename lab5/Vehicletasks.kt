package lab5

// var і значення за замовчуванням, щоб поля можна було задати всередині apply
data class Vehicle(
    var brand: String = "",
    var model: String = "",
    var year: Int = 0,
    var licensePlate: String = ""
)

fun main() {
    // Завдання 1: створення та налаштування (apply) + логування (also)
    val vehicle = Vehicle()
        .apply {
            brand = "Honda"
            model = "Civic"
            year = 2020
            licensePlate = "КА3344ВІ"
        }
        .also { println("Створено новий транспортний засіб: $it") }

    println()

    // Завдання 2: форматований вивід (with)
    with(vehicle) {
        println("Марка = $brand")
        println("Модель = $model")
        println("Рік випуску: $year")
        println("Номерний знак: $licensePlate")
    }
}