package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class PositiveIntKotlinSample {
    fun serialization() {
        val number: PositiveInt = 123.toPositiveInt()
            .getOrThrow()
        val encoded: String = Json.encodeToString(number)
        println(encoded) // 123
        val decoded: PositiveInt = Json.decodeFromString(encoded)
        println(decoded == number) // true
    } // END
}
