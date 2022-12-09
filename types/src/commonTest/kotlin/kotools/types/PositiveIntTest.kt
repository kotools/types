package kotools.types

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.test.Test

class PositiveIntTest {
    @Test
    fun compareTo_should_pass() {
        val x: PositiveInt = PositiveInt.random()
        val y: PositiveInt = PositiveInt.random()
        val result: Int = x.compareTo(y)
        val xValue: Int = x.toInt()
        val yValue: Int = y.toInt()
        result assertEquals xValue.compareTo(yValue)
    }

    @Test
    fun toString_should_behave_like_an_Int() {
        val x: PositiveInt = PositiveInt.random()
        val value: Int = x.toInt()
        "$x" assertEquals "$value"
    }

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: PositiveInt = PositiveInt.random()
        val result: String = Json.encodeToString(x)
        val value: Int = x.toInt()
        result assertEquals Json.encodeToString(value)
    }

    @Test
    fun deserialization_should_pass_with_a_positive_Int() {
        val value: Int = PositiveInt.range.random()
        val encoded: String = Json.encodeToString(value)
        val result: PositiveInt = Json.decodeFromString(encoded)
        result.toInt() assertEquals value
    }

    @Test
    fun deserialization_should_fail_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.range.random()
        val encoded: String = Json.encodeToString(value)
        val exception: IllegalArgumentException =
            assertFailsWith { Json.decodeFromString<PositiveInt>(encoded) }
        exception.message.assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    @Test
    fun int_toPositiveInt_should_pass_with_a_positive_Int() {
        val value: Int = PositiveInt.range.random()
        value.toPositiveInt()
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun int_toPositiveInt_should_fail_with_a_strictly_negative_Int() {
        val result: Result<PositiveInt> = StrictlyNegativeInt.range.random()
            .toPositiveInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
