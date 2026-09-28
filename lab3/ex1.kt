package lab3

fun multiplyWithRepeat(a: Int, b: Int): Int {
    var result = 0
    repeat(b) {          // повторює блок b разів
        result += a
    }
    return result
}

fun multiplyWithForEach(a: Int, b: Int): Int {
    var result = 0
    (1..b).forEach {     // проходить по діапазону від 1 до b
        result += a
    }
    return result
}

fun main() {
    print("Введіть два натуральні числа через пропуск: ")
    val (a, b) = readln()
        .trim()
        .split("\\s+".toRegex())
        .map { it.toInt() }

    if (a <= 0 || b <= 0) {
        println("Числа мають бути натуральними (більше нуля)")
        return
    }

    println("repeat:  $a * $b = ${multiplyWithRepeat(a, b)}")
    println("forEach: $a * $b = ${multiplyWithForEach(a, b)}")
}