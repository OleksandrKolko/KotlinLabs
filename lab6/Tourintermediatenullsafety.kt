package lab6

// ===== Вправа 1 =====
data class NotificationUser(val name: String?)

fun getNotificationPreferences(user: Any, emailEnabled: Boolean, smsEnabled: Boolean): List<String> {
    val validUser = user as? NotificationUser ?: return emptyList()
    val userName = validUser.name ?: "Guest"

    return listOfNotNull(
        "Email Notifications enabled for $userName".takeIf { emailEnabled },
        "SMS Notifications enabled for $userName".takeIf { smsEnabled }
    )
}

// ===== Вправа 2 =====
data class Subscription(val name: String, val isActive: Boolean)

fun getActiveSubscription(subscriptions: List<Subscription>): Subscription? =
    subscriptions.singleOrNull { it.isActive }

// ===== Вправа 3 =====
data class SocialUser(val username: String, val isActive: Boolean)

fun getActiveUsernames(users: List<SocialUser>): List<String> =
    users.mapNotNull { user -> user.username.takeIf { user.isActive } }

// ===== Вправа 4 =====
fun validateStock(requested: Int?, available: Int?): Int {
    val validRequested = requested ?: return -1
    val validAvailable = available ?: return -1

    if (validRequested < 0) return -1
    if (validRequested > validAvailable) return -1

    return validRequested
}

fun main() {
    println("--- Вправа 1 ---")
    val user1 = NotificationUser("Alice")
    val user2 = NotificationUser(null)
    val invalidUser = "NotAUser"
    println(getNotificationPreferences(user1, emailEnabled = true, smsEnabled = false))
    println(getNotificationPreferences(user2, emailEnabled = false, smsEnabled = true))
    println(getNotificationPreferences(invalidUser, emailEnabled = true, smsEnabled = true))

    println("\n--- Вправа 2 ---")
    val userWithPremiumPlan = listOf(
        Subscription("Basic Plan", false),
        Subscription("Premium Plan", true)
    )
    val userWithConflictingPlans = listOf(
        Subscription("Basic Plan", true),
        Subscription("Premium Plan", true)
    )
    println(getActiveSubscription(userWithPremiumPlan))
    println(getActiveSubscription(userWithConflictingPlans))

    println("\n--- Вправа 3 ---")
    val allUsers = listOf(
        SocialUser("alice123", true),
        SocialUser("bob_the_builder", false),
        SocialUser("charlie99", true)
    )
    println(getActiveUsernames(allUsers))

    println("\n--- Вправа 4 ---")
    println(validateStock(5, 10))
    println(validateStock(null, 10))
    println(validateStock(-2, 10))
    println(validateStock(15, 10))
}