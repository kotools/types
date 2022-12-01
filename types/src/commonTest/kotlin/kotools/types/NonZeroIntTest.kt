package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.test.Test

class NonZeroIntTest {
    // ---------- NonZeroInt.Companion.of(Int) ----------

    @Test
    fun of_should_pass_with_an_Int_other_than_zero() {
        val value: Int = NonZeroInt.random()
            .toInt()
        NonZeroInt.of(value)
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun of_should_fail_with_an_Int_that_equals_zero() {
        val result: Result<NonZeroInt> = NonZeroInt of 0
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
