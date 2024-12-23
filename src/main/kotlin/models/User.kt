package models

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random

data class User(
    var id: String? = null,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val address: String,
    val email: String,
    val phone: String,
    val login: String,
    val password: String
) {
    override fun toString(): String {
        return "User(name='$firstName', date='$dateOfBirth', address='$address', email='$email', phone='$phone', login='$login', password='$password')"
    }
}

object UserGsonConverter {

    private val gson: Gson by lazy {
        GsonBuilder()
            // Optional Gson configuration
            // .serializeNulls() // If you want to serialize null values as well
            .create()
    }

    fun toJson(user: User): String? = try {
        gson.toJson(user)
    } catch (e: Exception) {
        e.printStackTrace() // Use proper logging in production
        null
    }

    fun fromJson(jsonString: String): User? = try {
        gson.fromJson(jsonString, User::class.java)
    } catch (e: Exception) {
        e.printStackTrace() // Use proper logging in production
        null
    }

    fun toJsonList(users: List<User>): String? = try {
        gson.toJson(users)
    } catch (e: Exception) {
        e.printStackTrace() // Use proper logging in production
        null
    }

    fun fromJsonList(jsonString: String): List<User>? = try {
        val listType = object : TypeToken<List<User>>() {}.type
        gson.fromJson(jsonString, listType)
    } catch (e: Exception) {
        e.printStackTrace() // Use proper logging in production
        null
    }
}

object UserGenerator {

    private val firstNames = listOf("John", "Jane", "Peter", "Alice", "Bob", "Eva", "David", "Carol")
    private val lastNames = listOf("Doe", "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller")
    private val streetNames = listOf("Main St", "Oak Ave", "Pine Ln", "Maple Dr", "Cedar Rd")
    private val cities = listOf("New York", "Los Angeles", "Chicago", "Houston", "Phoenix")

    fun generateUser(): User {
        val firstName = firstNames.random()
        val lastName = lastNames.random()
        val streetNumber = Random.nextInt(1, 200)
        val streetName = streetNames.random()
        val city = cities.random()

        val birthDate =
            LocalDate.now().minusDays(Random.nextLong(365 * 18, 365 * 60)) // Random date between 18 and 60 years ago
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = birthDate.format(formatter)


        return User(
            id = UUID.randomUUID().toString(), // Generate unique ID
            firstName = firstName,
            dateOfBirth = formattedDate,
            lastName = lastName,
            address = "$streetNumber $streetName, $city",
            email = "${firstName.lowercase()}.${lastName.lowercase()}@example.com",
            phone = generatePhoneNumber(),
            login = "${firstName.lowercase()}${lastName.lowercase()}${Random.nextInt(100)}",
            password = UUID.randomUUID().toString().substring(0, 12) // Generates random password
        )
    }

    private fun generatePhoneNumber(): String {
        val areaCode = Random.nextInt(200, 999)
        val prefix = Random.nextInt(100, 999)
        val lineNumber = Random.nextInt(1000, 9999)
        return String.format("%03d-%03d-%04d", areaCode, prefix, lineNumber)
    }

    fun generateUsers(count: Int): List<User> = (1..count).map { generateUser() }

}

