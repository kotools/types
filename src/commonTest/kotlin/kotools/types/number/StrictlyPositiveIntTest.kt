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

internal val strictlyPositiveIntRange: IntRange = 1..Int.MAX_VALUE

class StrictlyPositiveIntTest {
    @Test
    fun toString_should_behave_like_an_Int(): Unit = strictlyPositiveIntRange
        .random()
        .toStrictlyPositiveInt()
        .getOrThrow()
        .run { assertEquals(actual = toString(), expected = "$asInt") }

    @Test
    fun int_toStrictlyPositiveInt_should_pass_with_a_strictly_positive_Int() {
        val value: Int = strictlyPositiveIntRange.random()
        assertEquals(
            actual = value.toStrictlyPositiveInt()
                .getOrThrow()
                .asInt,
            expected = value
        )
    }

    @Test
    fun int_toStrictlyPositiveInt_should_fail_with_a_negative_Int() {
        val result: Result<StrictlyPositiveInt> = negativeIntRange.random()
            .toStrictlyPositiveInt()
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .assertHasAMessage()
    }
}

class StrictlyPositiveIntSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_StrictlyPositiveInt_as_serial_name(): Unit =
        assertEquals(
            actual = StrictlyPositiveInt.serializer().descriptor.serialName,
            expected = "${Package.number}.StrictlyPositiveInt"
        )

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: StrictlyPositiveInt = strictlyPositiveIntRange.random()
            .toStrictlyPositiveInt()
            .getOrThrow()
        assertEquals(
            actual = Json.encodeToString(x),
            expected = Json.encodeToString(x.asInt)
        )
    }

    @Test
    fun deserialization_should_pass_with_a_strictly_positive_Int() {
        val value: Int = strictlyPositiveIntRange.random()
        val encoded: String = Json.encodeToString(value)
        val result: StrictlyPositiveInt = Json.decodeFromString(encoded)
        assertEquals(actual = result.asInt, expected = value)
    }

    @Test
    fun deserialization_should_fail_with_a_negative_Int() {
        val value: Int = negativeIntRange.random()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<StrictlyPositiveInt>(encoded)
        }
        exception.assertHasAMessage()
    }
}
