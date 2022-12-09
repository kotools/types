package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.test.Test

class StrictlyPositiveIntTest {
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
}
