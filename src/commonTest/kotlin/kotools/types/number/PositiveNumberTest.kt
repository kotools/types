package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotlin.random.Random
import kotlin.test.Test

class PositiveNumberTest {
    // ---------- Companion.invoke ----------

    @Test
    fun companionInvoke_should_pass_with_a_positive_Number() {
        val value: Int = Random.nextInt(from = 0, until = Int.MAX_VALUE)
        val result: PositiveNumber<Int> = PositiveNumber(value)
        result.value assertEquals value
    }

    @Test
    fun companionInvoke_should_throw_an_error_with_a_strictly_negative_Number() {
        val value: Int = Random.nextInt(from = Int.MIN_VALUE, until = 0)
        assertFailsWith<IllegalArgumentException> { PositiveNumber(value) }
    }

    // ---------- Companion.orNull ----------

    @Test
    fun companionOrNull_should_pass_with_a_positive_Number() {
        val value: Int = Random.nextInt(from = 0, until = Int.MAX_VALUE)
        val result: PositiveNumber<Int>? = PositiveNumber orNull value
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companionOrNull_should_return_null_with_a_strictly_negative_Number() {
        val value: Int = Random.nextInt(from = Int.MIN_VALUE, until = 0)
        val result: PositiveNumber<Int>? = PositiveNumber orNull value
        result.assertNull()
    }
}
