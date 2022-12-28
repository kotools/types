package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.assertHasAMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal val positiveIntRange: IntRange = ZeroInt.asInt..Int.MAX_VALUE

class PositiveIntTest {
    @Test
    fun int_toPositiveInt_should_pass_with_a_positive_Int() {
        val expected: Int = positiveIntRange.random()
        val actual: Int = expected.toPositiveInt()
            .getOrThrow()
            .asInt
        assertEquals(expected, actual)
    }

    @Test
    fun int_toPositiveInt_should_fail_with_a_strictly_negative_Int() {
        val result: Result<PositiveInt> = strictlyNegativeIntRange.random()
            .toPositiveInt()
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .assertHasAMessage()
    }
}

class PositiveIntSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_PositiveInt_as_serial_name(): Unit =
        assertEquals(
            expected = "${Package.number}.PositiveInt",
            actual = PositiveIntSerializer.descriptor.serialName
        )

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: PositiveInt = positiveIntRange.random()
            .toPositiveInt()
            .getOrThrow()
        assertEquals(
            expected = Json.encodeToString(x.asInt),
            actual = Json.encodeToString(PositiveIntSerializer, x)
        )
    }

    @Test
    fun deserialization_should_pass_with_a_positive_Int() {
        val expected: Int = positiveIntRange.random()
        val encoded: String = Json.encodeToString(expected)
        val result: PositiveInt =
            Json.decodeFromString(PositiveIntSerializer, encoded)
        assertEquals(expected, actual = result.asInt)
    }

    @Test
    fun deserialization_should_fail_with_a_strictly_negative_Int() {
        val value: Int = strictlyNegativeIntRange.random()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString(PositiveIntSerializer, encoded)
        }
        exception.assertHasAMessage()
    }
}
