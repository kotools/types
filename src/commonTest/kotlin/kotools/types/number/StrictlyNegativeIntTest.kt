package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.assertHasAMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal val strictlyNegativeIntRange: IntRange = Int.MIN_VALUE..-1

class StrictlyNegativeIntTest {
    @Test
    fun toString_should_behave_like_an_Int() {
        val value: Int = strictlyNegativeIntRange.random()
        assertEquals(
            actual = value.asStrictlyNegativeInt.getOrThrow()
                .toString(),
            expected = "$value"
        )
    }

    @Test
    fun int_toStrictlyNegativeInt_should_pass_with_a_strictly_negative_Int() {
        val value: Int = strictlyNegativeIntRange.random()
        assertEquals(
            actual = value.asStrictlyNegativeInt.getOrThrow().asInt,
            expected = value
        )
    }

    @Test
    fun int_toStrictlyNegativeInt_should_fail_with_a_positive_Int() {
        val result: Result<StrictlyNegativeInt> = positiveIntRange.random()
            .asStrictlyNegativeInt
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .assertHasAMessage()
    }
}

class StrictlyNegativeIntSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_StrictlyNegativeInt_as_serial_name(): Unit =
        assertEquals(
            actual = StrictlyNegativeInt.serializer().descriptor.serialName,
            expected = "${Package.number}.StrictlyNegativeInt"
        )

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: StrictlyNegativeInt = strictlyNegativeIntRange.random()
            .asStrictlyNegativeInt
            .getOrThrow()
        assertEquals(
            actual = Json.encodeToString(x),
            expected = Json.encodeToString(x.asInt)
        )
    }

    @Test
    fun deserialization_should_pass_with_a_strictly_negative_Int() {
        val value: Int = strictlyNegativeIntRange.random()
        val encoded: String = Json.encodeToString(value)
        val result: StrictlyNegativeInt = Json.decodeFromString(encoded)
        assertEquals(actual = result.asInt, expected = value)
    }

    @Test
    fun deserialization_should_fail_with_a_positive_Int() {
        val value: Int = positiveIntRange.random()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<StrictlyNegativeInt>(encoded)
        }
        exception.assertHasAMessage()
    }
}
