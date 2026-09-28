package lab7

// ===== Завдання 1: інтерфейси =====
interface Refundable {
    fun refund(amount: Double): Boolean
}

interface EReceiptable {
    val receiptEmail: String

    fun sendReceipt() {
        println(" [E-Receipt] Електронний чек надіслано на пошту: $receiptEmail")
    }
}

// ===== Завдання 2: абстрактний клас =====
abstract class PaymentMethod(
    val transactionId: String,
    val amount: Double,
    val currency: String = "UAH"
) {
    init {
        if (amount <= 0.0) {
            throw IllegalArgumentException("Сума платежу повинна бути більшою за нуль")
        }
    }

    abstract fun processPayment(): Boolean

    open fun printDetails() {
        println("Транзакція #$transactionId | Сума: $amount $currency")
    }
}

// ===== Завдання 3: конкретні платежі =====
class CreditCardPayment(
    transactionId: String,
    amount: Double,
    val cardNumber: String,
    override val receiptEmail: String,
    currency: String = "UAH"
) : PaymentMethod(transactionId, amount, currency), Refundable, EReceiptable {

    override fun processPayment(): Boolean {
        println("Списання $amount $currency з картки (закінчується на ${cardNumber.takeLast(4)})... Успішно!")
        return true
    }

    override fun refund(amount: Double): Boolean {
        println("Повернення $amount $currency на картку ${cardNumber.takeLast(4)} виконано.")
        return true
    }

    override fun printDetails() {
        super.printDetails()
        println("  └ Метод: Банківська картка (**** ${cardNumber.takeLast(4)})")
    }
}

class CryptoPayment(
    transactionId: String,
    amount: Double,
    val cryptoAddress: String,
    val networkFee: Double,
    currency: String = "UAH"
) : PaymentMethod(transactionId, amount, currency), Refundable {

    override fun processPayment(): Boolean {
        println(
            "Відправка $amount $currency (+ комісія $networkFee) на гаманець " +
                    "${cryptoAddress.take(6)}...${cryptoAddress.takeLast(4)}... Підтверджено!"
        )
        return true
    }

    override fun refund(amount: Double): Boolean {
        if (amount > networkFee) {
            println("Крипто-повернення ${amount - networkFee} $currency (з урахуванням комісії мережі) надіслано.")
            return true
        }
        println("Помилка: сума повернення $amount $currency не покриває комісію мережі $networkFee.")
        return false
    }

    override fun printDetails() {
        super.printDetails()
        println("  └ Метод: Криптовалюта (Адреса: ${cryptoAddress.take(6)}...)")
    }
}

class CashOnDeliveryPayment(
    transactionId: String,
    amount: Double,
    val deliveryAddress: String,
    override val receiptEmail: String,
    currency: String = "UAH"
) : PaymentMethod(transactionId, amount, currency), EReceiptable {

    override fun processPayment(): Boolean {
        println("Замовлення зареєстровано. Оплата $amount $currency буде здійснена кур'єру за адресою: $deliveryAddress.")
        return true
    }

    override fun printDetails() {
        super.printDetails()
        println("  └ Метод: Післяплата (Адреса: $deliveryAddress)")
    }

    override fun sendReceipt() {
        println(" [SMS & E-Receipt] Фіскальний чек зареєстровано та продубльовано на $receiptEmail")
    }
}

// ===== Завдання 4: масове повернення =====
fun processMassRefund(items: List<PaymentMethod>, refundPercentage: Double) {
    var successCount = 0
    for (payment in items) {
        val refundable = payment as? Refundable ?: continue
        val refundAmount = payment.amount * refundPercentage / 100
        if (refundable.refund(refundAmount)) {
            successCount++
        }
    }
    println("Успішно оброблено повернень: $successCount з ${items.size} транзакцій.")
}

fun main() {
    val payments: List<PaymentMethod> = listOf(
        CreditCardPayment(
            transactionId = "TXN-001",
            amount = 2500.0,
            cardNumber = "4149499988884321",
            receiptEmail = "client@gmail.com"
        ),
        CryptoPayment(
            transactionId = "TXN-002",
            amount = 10000.0,
            cryptoAddress = "0x71C...B29C",
            networkFee = 50.0
        ),
        CashOnDeliveryPayment(
            transactionId = "TXN-003",
            amount = 1200.0,
            deliveryAddress = "м. Львів, Відділення №5",
            receiptEmail = "receiver@lviv.ua"
        )
    )

    println("==================================================")
    println("        ОБРОБКА ПОТОЧНИХ ТРАНЗАКЦІЙ")
    println("==================================================")
    for (payment in payments) {
        payment.printDetails()
        payment.processPayment()
        if (payment is EReceiptable) {
            payment.sendReceipt()   // smart cast до EReceiptable
        }
        println("--------------------------------------------------")
    }

    println()
    println("==================================================")
    println("       МАСОВЕ ПОВЕРНЕННЯ КОШТІВ (50%)")
    println("==================================================")
    processMassRefund(payments, 50.0)

    println()
    try {
        CashOnDeliveryPayment("TXN-004", 0.0, "м. Київ", "test@ukr.net")
    } catch (e: IllegalArgumentException) {
        println("Помилка створення платежу: ${e.message}")
    }
}