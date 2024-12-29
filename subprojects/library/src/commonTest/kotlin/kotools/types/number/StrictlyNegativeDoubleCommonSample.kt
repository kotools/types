package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
internal class StrictlyNegativeDoubleCommonSample {
    @Test
    fun serialization() {
        val number: StrictlyNegativeDouble = StrictlyNegativeDouble.create(-4.2)
        val encoded: String = Json.encodeToString(number)
        assertEquals(expected = "-4.2", actual = encoded)
        val decoded: StrictlyNegativeDouble = Json.decodeFromString(encoded)
        assertEquals(expected = number, actual = decoded)
    }

    @Test
    fun equalsOverride() {
        val number: Number = -23
        val first: StrictlyNegativeDouble =
            StrictlyNegativeDouble.create(number)
        val second: StrictlyNegativeDouble =
            StrictlyNegativeDouble.create(number)
        val actual: Boolean = first == second // or first.equals(second)
        assertTrue(actual)
    }

    @Test
    fun hashCodeOverride() {
        val number: Number = -23
        val first: StrictlyNegativeDouble =
            StrictlyNegativeDouble.create(number)
        val second: StrictlyNegativeDouble =
            StrictlyNegativeDouble.create(number)
        val actual: Boolean = first.hashCode() == second.hashCode()
        assertTrue(actual)
    }

    @Test
    fun toDouble() {
        val number = -7
        val actual: Double = StrictlyNegativeDouble.create(number)
            .toDouble()
        val expected: Double = number.toDouble()
        assertEquals(expected, actual)
    }

    @Test
    fun toStringOverride() {
        val number = -23
        val actual: String = StrictlyNegativeDouble.create(number)
            .toString()
        val expected: String = number.toDouble()
            .toString()
        assertEquals(expected, actual)
    }
}
