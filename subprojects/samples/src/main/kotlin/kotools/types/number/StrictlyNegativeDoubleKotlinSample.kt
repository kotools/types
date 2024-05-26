package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal class StrictlyNegativeDoubleKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun serialization() {
        val number: StrictlyNegativeDouble = StrictlyNegativeDouble.create(-42)
        val encoded: String = Json.encodeToString(number)
        println(encoded) // -42.0
        val decoded: StrictlyNegativeDouble = Json.decodeFromString(encoded)
        println(decoded == number) // true
    } // END
}
