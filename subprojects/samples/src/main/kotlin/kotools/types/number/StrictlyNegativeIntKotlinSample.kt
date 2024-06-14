package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class StrictlyNegativeIntKotlinSample {
    fun serialization() {
        val number: StrictlyNegativeInt = (-123).toStrictlyNegativeInt()
            .getOrThrow()
        val encoded: String = Json.encodeToString(number)
        println(encoded) // -123
        val decoded: StrictlyNegativeInt = Json.decodeFromString(encoded)
        println(decoded == number) // true
    } // END
}
