package lab7.tour

// ===== Вправа 1: абстрактний клас =====
abstract class SmartDevice(val name: String) {
    abstract fun turnOn()
    abstract fun turnOff()
}

class SmartLight(name: String) : SmartDevice(name) {
    override fun turnOn() {
        println("$name is now ON.")
    }

    override fun turnOff() {
        println("$name is now OFF.")
    }

    fun adjustBrightness(level: Int) {
        println("Adjusting $name brightness to $level%.")
    }
}

class SmartThermostat(name: String) : SmartDevice(name) {
    override fun turnOn() {
        println("$name thermostat is now heating.")
    }

    override fun turnOff() {
        println("$name thermostat is now off.")
    }

    fun adjustTemperature(temperature: Int) {
        println("$name thermostat set to $temperature°C.")
    }
}

// ===== Вправа 2: інтерфейс =====
interface Media {
    val title: String
    fun play()
}

class Audio(override val title: String, val composer: String) : Media {
    override fun play() {
        println("Playing audio: $title, composed by $composer")
    }
}

// ===== Вправа 3: інтерфейс + абстрактний клас =====
interface Refundable {
    fun refund(amount: Double)
}

abstract class PaymentMethod(val name: String) {
    fun authorize(amount: Double) {
        println("Authorizing payment of \$$amount.")
    }

    abstract fun processPayment(amount: Double)
}

class CreditCard(name: String) : PaymentMethod(name), Refundable {
    override fun processPayment(amount: Double) {
        println("Processing credit card payment of \$$amount.")
    }

    override fun refund(amount: Double) {
        println("Refunding \$$amount to the credit card.")
    }
}

// ===== Вправа 4: делегування =====
interface Messenger {
    fun sendMessage(message: String)
    fun receiveMessage(): String
}

class BasicMessenger : Messenger {
    override fun sendMessage(message: String) {
        println("Sending message: $message")
    }

    override fun receiveMessage(): String {
        return "You've got a new message!"
    }
}

// receiveMessage() не пишемо, його бере з basicMessenger
class SmartMessenger(val basicMessenger: BasicMessenger) : Messenger by basicMessenger {
    override fun sendMessage(message: String) {
        println("Sending a smart message: $message")
        basicMessenger.sendMessage("[smart] $message")
    }
}

fun main() {
    println("--- Вправа 1 ---")
    val livingRoomLight = SmartLight("Living Room Light")
    val bedroomThermostat = SmartThermostat("Bedroom Thermostat")
    livingRoomLight.turnOn()
    livingRoomLight.adjustBrightness(10)
    livingRoomLight.turnOff()
    bedroomThermostat.turnOn()
    bedroomThermostat.adjustTemperature(5)
    bedroomThermostat.turnOff()

    println("\n--- Вправа 2 ---")
    val audio = Audio("Symphony No. 5", "Beethoven")
    audio.play()

    println("\n--- Вправа 3 ---")
    val visa = CreditCard("Visa")
    visa.authorize(100.0)
    visa.processPayment(100.0)
    visa.refund(50.0)

    println("\n--- Вправа 4 ---")
    val basicMessenger = BasicMessenger()
    val smartMessenger = SmartMessenger(basicMessenger)
    basicMessenger.sendMessage("Hello!")
    println(smartMessenger.receiveMessage())
    smartMessenger.sendMessage("Hello from SmartMessenger!")
}