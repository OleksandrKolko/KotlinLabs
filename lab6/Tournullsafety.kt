package lab6

data class Employee(val name: String, var salary: Int)

fun employeeById(id: Int) = when (id) {
    1 -> Employee("Mary", 20)
    2 -> null
    3 -> Employee("John", 21)
    4 -> Employee("Ann", 23)
    else -> null
}

// якщо працівника немає, повертаємо 0
fun salaryById(id: Int) = employeeById(id)?.salary ?: 0

fun main() {
    println((1..5).sumOf { id -> salaryById(id) })   // 64
}