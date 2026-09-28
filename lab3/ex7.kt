package lab3

import java.util.Locale

fun main() {
    val rawLogs = listOf(
        "2026-09-01 INFO: User logged in",
        "2026-09-01 ERROR: 500 Internal Server Error",
        "2026-09-01 WARN: High memory usage",
        "2026-09-01 ERROR: 404 Not Found",
        "2026-09-01 INFO: Payment processed"
    )

    var infoCount = 0
    var warnCount = 0
    var errorCount = 0
    val errors = mutableListOf<String>()

    for (log in rawLogs) {
        when {
            log.contains("ERROR") -> {
                errorCount++
                errors.add(log.substringAfter("ERROR: "))
            }
            log.contains("WARN") -> warnCount++
            log.contains("INFO") -> infoCount++
        }
    }

    val total = rawLogs.size
    fun percent(n: Int) = String.format(Locale.US, "%.2f", n * 100.0 / total)

    val errorList = errors.joinToString("\n") { "  - $it" }

    val report = """
        |========== ЗВІТ ПО ЛОГАХ ==========
        |Всього записів: $total
        |INFO:  $infoCount (${percent(infoCount)}%)
        |WARN:  $warnCount (${percent(warnCount)}%)
        |ERROR: $errorCount (${percent(errorCount)}%)
        |-----------------------------------
        |Список помилок:
        |$errorList
        |===================================
    """.trimMargin()

    println(report)
}