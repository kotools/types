package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.test.Test

class PositiveIntTest {
    // ---------- PositiveInt.Companion.of(Int) ----------

    @Test
    fun of_should_pass_with_a_positive_Int() {
        val value: Int = PositiveInt.random().toInt()
        PositiveInt.of(value)
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun of_should_fail_with_a_strictly_negative_Int() {
        val result: Result<PositiveInt> = PositiveInt of StrictlyNegativeInt
            .random()
            .toInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
