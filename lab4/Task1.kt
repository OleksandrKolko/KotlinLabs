package lab4

import kotlin.random.Random

// ⭐ Додаткове завдання: три можливі варіанти результату розв'язання
sealed interface Roots
data class TwoRoots(val x1: Double, val x2: Double) : Roots
data class OneRoot(val x: Double) : Roots
data object NoRealRoots : Roots

class QuadraticEquation(a: Double, var b: Double, var c: Double) {

    // Частина 3: a змінюване, але з перевіркою в сетері
    var a: Double = a
        set(value) {
            require(value != 0.0) { "Коефіцієнт 'a' не може бути нулем!" }
            field = value
        }

    // Частина 1: перевірка при створенні об'єкта
    init {
        require(a != 0.0) { "Коефіцієнт 'a' не може бути нулем!" }
    }

    // Вторинний конструктор для цілих коефіцієнтів
    constructor(a: Int, b: Int, c: Int) : this(a.toDouble(), b.toDouble(), c.toDouble())

    // Вторинний конструктор для зведеного рівняння (a = 1)
    constructor(b: Double, c: Double) : this(1.0, b, c)

    // Обчислювана властивість: перераховується при кожному зверненні
    val discriminant: Double
        get() = b * b - 4 * a * c

    fun solve(): List<Double> {
        val d = discriminant
        return when {
            d > 0 -> {
                val sqrtD = Math.sqrt(d)
                listOf((-b - sqrtD) / (2 * a) + 0.0, (-b + sqrtD) / (2 * a) + 0.0)
            }
            d == 0.0 -> listOf(-b / (2 * a) + 0.0)   // + 0.0 прибирає "-0.0"
            else -> emptyList()
        }
    }

    // ⭐ Версія розв'язання з sealed interface
    fun solveRoots(): Roots {
        val d = discriminant
        return when {
            d > 0 -> {
                val sqrtD = Math.sqrt(d)
                TwoRoots((-b - sqrtD) / (2 * a) + 0.0, (-b + sqrtD) / (2 * a) + 0.0)
            }
            d == 0.0 -> OneRoot(-b / (2 * a) + 0.0)
            else -> NoRealRoots
        }
    }

    override fun toString(): String {
        val aPart = when (a) {
            1.0 -> "x^2"
            -1.0 -> "-x^2"
            else -> "${a}x^2"
        }
        val bPart = when {
            b > 0 -> " + ${b}x"
            b < 0 -> " - ${-b}x"
            else -> ""
        }
        val cPart = when {
            c > 0 -> " + $c"
            c < 0 -> " - ${-c}"
            else -> ""
        }
        return "$aPart$bPart$cPart = 0"
    }
}

// Випадкове ціле від -50 до 50, але не нуль
fun randomNonZero(): Int {
    var value: Int
    do {
        value = Random.nextInt(-50, 51)
    } while (value == 0)
    return value
}

fun main() {
    println("=== Частина 1: конструктори ===")
    val eq1 = QuadraticEquation(2.0, -4.0, 2.0)
    val eq2 = QuadraticEquation(1, -3, 2)      // вторинний конструктор з Int
    val eq3 = QuadraticEquation(5.0, -6.0)     // зведене рівняння
    println(eq1)
    println(eq2)
    println(eq3)

    try {
        QuadraticEquation(0.0, 1.0, 1.0)
    } catch (e: IllegalArgumentException) {
        println("Помилка при створенні: ${e.message}")
    }

    println("\n=== Частина 2: дискримінант і корені ===")
    for (eq in listOf(eq1, eq2, eq3, QuadraticEquation(1.0, 1.0, 5.0))) {
        println("$eq  |  D = ${eq.discriminant}  |  корені: ${eq.solve()}")
    }

    println("\n=== Частина 3: зміна коефіцієнтів ===")
    val eq = QuadraticEquation(1.0, 2.0, 1.0)
    println("$eq  |  D = ${eq.discriminant}")
    eq.b = 5.0
    eq.c = -6.0
    println("$eq  |  D = ${eq.discriminant}  (перераховано автоматично)")
    eq.a = 2.0
    println("$eq  |  D = ${eq.discriminant}")
    try {
        eq.a = 0.0
    } catch (e: IllegalArgumentException) {
        println("Помилка в сетері: ${e.message}")
    }

    println("\n=== Частина 4: 100 випадкових рівнянь ===")
    val equations = List(100) {
        QuadraticEquation(randomNonZero(), Random.nextInt(-50, 51), Random.nextInt(-50, 51))
    }
    val withTwoRoots = equations.filter { it.discriminant > 0 }
    withTwoRoots.forEach { println("$it  ->  ${it.solve()}") }
    println("Рівнянь з двома коренями: ${withTwoRoots.size}")

    println("\n=== ⭐ Sealed interface ===")
    for (e in listOf(eq2, eq1, QuadraticEquation(1.0, 1.0, 5.0))) {
        val message = when (val roots = e.solveRoots()) {
            is TwoRoots -> "два корені: x1 = ${roots.x1}, x2 = ${roots.x2}"
            is OneRoot -> "один корінь: x = ${roots.x}"
            NoRealRoots -> "дійсних коренів немає"
        }
        println("$e  ->  $message")
    }
}