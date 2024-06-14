package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class NegativeIntKotlinSample {
    fun serialization() {
        val number: NegativeInt = (-123).toNegativeInt()
            .getOrThrow()
        val encoded: String = Json.encodeToString(number)
        println(encoded) // -123
        val decoded: NegativeInt = Json.decodeFromString(encoded)
        println(decoded == number) // true
    } // END
}
