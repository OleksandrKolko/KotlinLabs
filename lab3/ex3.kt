package lab3

import kotlin.random.Random

fun main() {
    // 100 випадкових чисел від -100 до 100 (101 не входить)
    val numbers = Array(100) { Random.nextInt(-100, 101) }
    println("Початковий масив: ${numbers.joinToString()}")

    val positive = numbers.filter { it > 0 }
    println("Додатні: $positive")

    val divisibleBy5 = positive.filter { it % 5 == 0 }
    println("Діляться на 5: $divisibleBy5")

    val squares = divisibleBy5.map { it * it }
    println("Квадрати: $squares")

    val sortedDesc = squares.sortedDescending()
    println("За спаданням: $sortedDesc")

    println("Найбільше: ${sortedDesc.maxOrNull() ?: "немає"}")
    println("Найменше: ${sortedDesc.minOrNull() ?: "немає"}")

    val strings = sortedDesc.map { it.toString() }
    println("Колекція рядків: $strings")

    val result = strings.joinToString(" ")
    println("Один рядок: $result")
}