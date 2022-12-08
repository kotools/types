package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test

class PositiveIntTest {
    // ---------- PositiveInt.Companion.of(Int) ----------

    @Test
    fun of_should_pass_with_a_positive_Int() {
        val value: Int = Random.nextInt(0..Int.MAX_VALUE)
        PositiveInt.of(value)
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun of_should_fail_with_a_strictly_negative_Int() {
        val value: Int = Random.nextInt(Int.MIN_VALUE..-1)
        val result: Result<PositiveInt> = PositiveInt of value
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
