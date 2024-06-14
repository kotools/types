package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class AnyIntKotlinSample {
    fun serialization() {
        val number: AnyInt = 123.toNonZeroInt()
            .getOrThrow()
        val encoded: String = Json.encodeToString(number)
        println(encoded) // 123
        val decoded: AnyInt = Json.decodeFromString(encoded)
        println(decoded == number) // true
    } // END
}
