package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class NonZeroIntKotlinSample {
    fun serialization() {
        val number: NonZeroInt = 123.toNonZeroInt()
            .getOrThrow()
        val encoded: String = Json.encodeToString(number)
        println(encoded) // 123
        val decoded: NonZeroInt = Json.decodeFromString(encoded)
        println(decoded == number) // true
    } // END
}
