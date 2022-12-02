package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.test.Test

class NegativeIntTest {
    // ---------- NegativeInt.Companion.of(Int) ----------

    @Test
    fun of_should_pass_with_a_negative_Int() {
        val value: Int = NegativeInt.random().toInt()
        NegativeInt.of(value)
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun of_should_fail_with_a_strictly_positive_Int() {
        val result: Result<NegativeInt> = StrictlyPositiveInt.random()
            .toInt()
            .let(NegativeInt.Companion::of)
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
