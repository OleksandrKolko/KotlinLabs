package lab5

data class ArraySummary(
    val maxElement: Int,
    val elementsOverLimit: Int,
    val totalSum: Int
)

fun generateRandomArray(size: Int, maxValue: Int): IntArray {
    require(size > 0 && maxValue > 0) { "Розмір та максимальне значення мають бути більшими за нуль" }
    return IntArray(size) { kotlin.random.Random.nextInt(1, maxValue + 1) }
}

fun main() {
    generateRandomArray(size = 8, maxValue = 40)
        // Крок 1: логування початкового масиву (it)
        .also { println("Початковий масив: ${it.contentToString()}") }
        // Крок 2: зміна елементів на місці (this)
        .apply {
            for (i in indices) {
                this[i] = if (this[i] % 2 != 0) this[i] * 2 else this[i] / 2
            }
        }
        // Крок 3: логування зміненого масиву (it)
        .also { println("Модифікований масив: ${it.contentToString()}") }
        // Крок 4: обчислення статистики (this), повертає ArraySummary
        .run {
            ArraySummary(
                maxElement = reduce { max, x -> maxOf(max, x) },
                elementsOverLimit = count { it > 20 },
                totalSum = sum()
            )
        }
        // Крок 5: перетворення статистики на рядок (it)
        .let {
            """
            ----------------------------------------
            ЗВІТ ОБРОБКИ МАСИВУ:
             • Найбільше значення: ${it.maxElement}
             • Кількість елементів > 20: ${it.elementsOverLimit}
             • Сумарне значення: ${it.totalSum}
            ----------------------------------------
            """.trimIndent()
        }
        // Крок 6: друк звіту
        .also { println(it) }
}