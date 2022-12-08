package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.random.Random
import kotlin.test.Test

class NonZeroIntTest {
    @Test
    fun toString_should_behave_like_an_Int() {
        var value: Int = Random.nextInt()
        while (value == 0) value = Random.nextInt()
        val result: String = value.toNonZeroIntOrThrow()
            .toString()
        result assertEquals value.toString()
    }

    @Test
    fun int_toNonZeroInt_should_pass_with_an_Int_other_than_zero() {
        var value: Int = Random.nextInt()
        while (value == 0) value = Random.nextInt()
        value.toNonZeroInt()
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun int_toNonZeroInt_should_fail_with_an_Int_that_equals_zero() {
        val result: Result<NonZeroInt> = 0.toNonZeroInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}

@Throws(IllegalArgumentException::class)
private fun Int.toNonZeroIntOrThrow(): NonZeroInt = toNonZeroInt()
    .getOrThrow()
