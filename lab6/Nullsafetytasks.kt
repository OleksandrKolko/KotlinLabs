package lab6

data class Address(
    val street: String,
    val city: String?,
    val postalCode: String?
)

data class Client(
    val id: Int,
    val name: String,
    val email: String?,
    val address: Address?,
    val extraData: Any? // Може містити рядок, число або null
)

val clientList = listOf(
    Client(
        id = 1,
        name = "Олена",
        email = "olena@example.com",
        address = Address("вул. Саксаганського, 10", "Київ", "01033"),
        extraData = "VIP-клієнт"
    ),
    Client(
        id = 2,
        name = "Богдан",
        email = null,
        address = Address("вул. Городоцька, 45", "Львів", null),
        extraData = 42
    ),
    Client(
        id = 3,
        name = "Марія",
        email = "maria@example.com",
        address = null,
        extraData = null
    ),
    Client(
        id = 4,
        name = "Дмитро",
        email = null,
        address = Address("вул. Соборна, 1", null, null),
        extraData = "Очікує дзвінка"
    )
)

// Завдання 1
fun getShippingLabel(client: Client): String {
    val address = client.address ?: return "Самовивіз: Клієнт ${client.name} не надав адреси"
    val city = address.city ?: "Місто не вказано"
    val postalCode = address.postalCode ?: "Індекс невідомий"
    return "${address.street}, $city, $postalCode"
}

// Завдання 2
fun printClientNote(client: Client) {
    val note = client.extraData as? String ?: "Додаткові примітки відсутні"
    println("Клієнт ${client.name} -> Примітка: $note")
}

// Завдання 4
fun getClientEmailOrThrow(client: Client): String {
    return client.email
        ?: throw IllegalArgumentException("Клієнт з ID ${client.id} не має електронної пошти!")
}

// Завдання 5
// !! можна використовувати тільки тоді, коли за логікою програми значення точно не null,
// але компілятор цього сам довести не може. Якщо там все ж буде null, це помилка в коді,
// і краще щоб програма одразу впала, ніж працювала з неправильними даними.
fun forceGetPostalCode(client: Client): String {
    return client.address!!.postalCode!!
}

fun main() {
    println("--- Завдання 1 ---")
    clientList.take(3).forEach {
        println("Адреса ${it.name}: ${getShippingLabel(it)}")
    }

    println("\n--- Завдання 2 ---")
    clientList.forEach { printClientNote(it) }

    println("\n--- Завдання 3 ---")
    val emails: List<String> = clientList
        .map { it.email }        // List<String?>
        .filterNotNull()         // List<String>
    val shortestLength = emails.minByOrNull { it.length }?.length ?: 0
    println("База email для розсилки: $emails")
    println("Довжина найкоротшого email: $shortestLength")

    println("\n--- Завдання 4 ---")
    try {
        getClientEmailOrThrow(clientList.first { it.id == 2 })
    } catch (e: IllegalArgumentException) {
        println("Перехоплено виняток: ${e.message}")
    }

    println("\n--- Завдання 5 ---")
    val olena = clientList.first { it.id == 1 }
    println("Індекс клієнта ${olena.name}: ${forceGetPostalCode(olena)}")
    try {
        forceGetPostalCode(clientList.first { it.id == 2 })
    } catch (e: NullPointerException) {
        println("Перехоплено очікуваний NPE: ${e.message}")
    }
}