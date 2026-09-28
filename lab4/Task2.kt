package lab4

// ===== Частина 1: звичайний клас =====
class EmployeePlain(val firstName: String, val lastName: String, val position: String)

// ===== Частина 2: data class =====
data class EmployeeData(val firstName: String, val lastName: String, val position: String) {
    var bonus: Int = 0   // поза конструктором: не бере участі в equals/hashCode/toString
}

// ===== Частина 3: звичайний клас, що вручну повторює функціонал data class =====
class Employee(val firstName: String, val lastName: String, val position: String) {

    override fun toString(): String =
        "Employee(firstName=$firstName, lastName=$lastName, position=$position)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Employee) return false
        return firstName == other.firstName &&
                lastName == other.lastName &&
                position == other.position
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + position.hashCode()
        return result
    }

    operator fun component1() = firstName
    operator fun component2() = lastName
    operator fun component3() = position

    fun copy(
        firstName: String = this.firstName,
        lastName: String = this.lastName,
        position: String = this.position
    ) = Employee(firstName, lastName, position)
}

fun main() {
    println("===== Частина 1: звичайний клас =====")
    val p1 = EmployeePlain("Іван", "Петренко", "Junior Developer")
    val p2 = EmployeePlain("Іван", "Петренко", "Junior Developer")
    val p3 = p1
    println(p1)                            // lab4.EmployeePlain@1b6d3586 (адреса в пам'яті)
    println("p1 === p2: ${p1 === p2}")     // false: різні об'єкти
    println("p1 == p2:  ${p1 == p2}")      // false: equals порівнює посилання
    println("p1 === p3: ${p1 === p3}")     // true: те саме посилання

    println("\n===== Частина 2: data class =====")
    val d1 = EmployeeData("Іван", "Петренко", "Junior Developer")
    val d2 = EmployeeData("Іван", "Петренко", "Junior Developer")
    println(d1)                            // EmployeeData(firstName=Іван, ...)
    println("d1 === d2: ${d1 === d2}")     // false: різні об'єкти
    println("d1 == d2:  ${d1 == d2}")      // true: порівнюються дані

    val (name, surname, pos) = d1
    println("Ім'я: $name, Прізвище: $surname, Посада: $pos")

    val promoted = d1.copy(position = "Middle Developer")
    println("Після підвищення: $promoted")

    val b1 = EmployeeData("Олена", "Коваль", "QA").apply { bonus = 100 }
    val b2 = EmployeeData("Олена", "Коваль", "QA").apply { bonus = 500 }
    println("Різний bonus, b1 == b2: ${b1 == b2}")   // true: bonus ігнорується

    println("\n===== Частина 3: ручна реалізація =====")
    val emp1 = Employee("Іван", "Петренко", "Junior Developer")
    val emp2 = Employee("Іван", "Петренко", "Junior Developer")
    println(emp1)
    println("emp1 === emp2: ${emp1 === emp2}")
    println("emp1 == emp2:  ${emp1 == emp2}")
    println("hashCode однакові: ${emp1.hashCode() == emp2.hashCode()}")

    val (n, s, p) = emp1
    println("Ім'я: $n, Прізвище: $s, Посада: $p")

    val emp4 = emp1.copy(position = "Middle Developer")
    println("Після підвищення: $emp4")

    // Перевірка, що коректно працює в HashSet (потрібна пара equals + hashCode)
    println("Розмір множини з emp1 та emp2: ${setOf(emp1, emp2).size}")   // 1
}