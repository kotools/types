package kotools.types

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.test.Test

class NegativeIntTest {
    @Test
    fun compareTo_should_pass() {
        val x: NegativeInt = NegativeInt.random()
        val y: NegativeInt = NegativeInt.random()
        val xValue: Int = x.toInt()
        val yValue: Int = y.toInt()
        x.compareTo(y) assertEquals xValue.compareTo(yValue)
    }

    @Test
    fun toString_should_pass() {
        val x: NegativeInt = NegativeInt.random()
        val value: Int = x.toInt()
        "$x" assertEquals "$value"
    }

    @Test
    fun int_toNegativeInt_should_pass_with_a_negative_Int() {
        val value: Int = NegativeInt.range.random()
        value.toNegativeInt()
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun int_toNegativeInt_should_fail_with_a_strictly_positive_Int() {
        val result: Result<NegativeInt> = StrictlyPositiveInt.range.random()
            .toNegativeInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: NegativeInt = NegativeInt.random()
        val result: String = Json.encodeToString(x)
        result assertEquals Json.encodeToString(x.toInt())
    }

    @Test
    fun deserialization_should_pass_with_a_negative_Int() {
        val value: Int = NegativeInt.range.random()
        val encoded: String = Json.encodeToString(value)
        val result: NegativeInt = Json.decodeFromString(encoded)
        result.toInt() assertEquals value
    }

    @Test
    fun deserialization_should_fail_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.range.random()
        val encoded: String = Json.encodeToString(value)
        val exception: IllegalArgumentException =
            assertFailsWith { Json.decodeFromString<NegativeInt>(encoded) }
        exception.message.assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
