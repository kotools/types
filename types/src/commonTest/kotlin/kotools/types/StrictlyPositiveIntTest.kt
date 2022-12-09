package kotools.types

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.test.Test

class StrictlyPositiveIntTest {
    @Test
    fun compareTo_should_pass() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val y: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val result: Int = x.compareTo(y)
        val xValue: Int = x.toInt()
        val yValue: Int = y.toInt()
        result assertEquals xValue.compareTo(yValue)
    }

    @Test
    fun toString_should_behave_like_an_Int() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random()
        "$x" assertEquals x.toInt()
            .toString()
    }

    @Test
    fun int_toStrictlyPositiveInt_should_pass_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.range.random()
        value.toStrictlyPositiveInt()
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun int_toStrictlyPositiveInt_should_fail_with_a_negative_Int() {
        val result: Result<StrictlyPositiveInt> = NegativeInt.range.random()
            .toStrictlyPositiveInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val result: String = Json.encodeToString(x)
        result assertEquals Json.encodeToString(x.toInt())
    }

    @Test
    fun deserialization_should_pass_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.range.random()
        val encoded: String = Json.encodeToString(value)
        val result: StrictlyPositiveInt = Json.decodeFromString(encoded)
        result.toInt() assertEquals value
    }

    @Test
    fun deserialization_should_fail_with_a_negative_Int() {
        val value: Int = NegativeInt.range.random()
        val encoded: String = Json.encodeToString(value)
        val exception: IllegalArgumentException = assertFailsWith {
            Json.decodeFromString<StrictlyPositiveInt>(encoded)
        }
        exception.message.assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
