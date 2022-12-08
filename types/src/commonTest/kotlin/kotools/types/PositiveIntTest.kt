package kotools.types

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test

class PositiveIntTest {
    @Test
    fun toString_should_behave_like_an_Int() {
        val value: Int = Random.nextInt(0..Int.MAX_VALUE)
        value.toPositiveIntOrThrow()
            .toString() assertEquals "$value"
    }

    @Test
    fun serialization_should_behave_like_an_Int() {
        val value: Int = Random.nextInt(0..Int.MAX_VALUE)
        val x: PositiveInt = value.toPositiveIntOrThrow()
        val result: String = Json.encodeToString(x)
        result assertEquals Json.encodeToString(value)
    }

    @Test
    fun deserialization_should_pass_with_a_positive_Int() {
        val value: Int = Random.nextInt(0..Int.MAX_VALUE)
        val encoded: String = Json.encodeToString(value)
        val result: PositiveInt = Json.decodeFromString(encoded)
        result.toInt() assertEquals value
    }

    @Test
    fun deserialization_should_fail_with_a_strictly_negative_Int() {
        val value: Int = Random.nextInt(Int.MIN_VALUE..-1)
        val encoded: String = Json.encodeToString(value)
        val exception: IllegalArgumentException =
            assertFailsWith { Json.decodeFromString<PositiveInt>(encoded) }
        exception.message.assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    @Test
    fun int_toPositiveInt_should_pass_with_a_positive_Int() {
        val value: Int = Random.nextInt(0..Int.MAX_VALUE)
        value.toPositiveInt()
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun int_toPositiveInt_should_fail_with_a_strictly_negative_Int() {
        val result: Result<PositiveInt> = Random.nextInt(Int.MIN_VALUE..-1)
            .toPositiveInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}

@Throws(IllegalArgumentException::class)
private fun Int.toPositiveIntOrThrow(): PositiveInt = toPositiveInt()
    .getOrThrow()
