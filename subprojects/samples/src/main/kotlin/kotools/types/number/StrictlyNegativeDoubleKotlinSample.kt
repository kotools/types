package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.experimental.ExperimentalKotoolsTypesApi

@OptIn(ExperimentalKotoolsTypesApi::class)
internal class StrictlyNegativeDoubleKotlinSample {
    fun serialization() {
        val number: StrictlyNegativeDouble = StrictlyNegativeDouble.create(-42)
        val encoded: String = Json.encodeToString(number)
        println(encoded) // -42.0
        val decoded: StrictlyNegativeDouble = Json.decodeFromString(encoded)
        println(decoded == number) // true
    } // END

    fun equalsOverride() {
        val number: Number = -23
        val first: StrictlyNegativeDouble =
            StrictlyNegativeDouble.create(number) // TABS: 1
        val second: StrictlyNegativeDouble =
            StrictlyNegativeDouble.create(number) // TABS: 1
        val result: Boolean = first == second // or first.equals(second)
        println(result) // true
    } // END

    fun hashCodeOverride() {
        val number: Number = -23
        val first: StrictlyNegativeDouble =
            StrictlyNegativeDouble.create(number) // TABS: 1
        val second: StrictlyNegativeDouble =
            StrictlyNegativeDouble.create(number) // TABS: 1
        val result: Boolean = first.hashCode() == second.hashCode()
        println(result) // true
    } // END

    fun toDouble() {
        val number: StrictlyNegativeDouble = StrictlyNegativeDouble.create(-7)
        val result: Double = number.toDouble()
        println(result) // -7.0
    } // END
}
