package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class StrictlyPositiveIntKotlinSample {
    fun serialization() {
        val number: StrictlyPositiveInt = 123.toStrictlyPositiveInt()
            .getOrThrow()
        val encoded: String = Json.encodeToString(number)
        println(encoded) // 123
        val decoded: StrictlyPositiveInt = Json.decodeFromString(encoded)
        println(decoded == number) // true
    } // END
}
