package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.random.Random
import kotlin.test.Test

private fun Random.nonZeroInt(): Int {
    var value: Int = nextInt()
    while (value == 0) value = nextInt()
    return value
}

private fun Random.nonZeroNumberOfInt(): NonZeroNumber<Int> = nonZeroInt()
    .toNonZeroNumber()
    .getOrThrow()

class NumberToNonZeroNumberTest {
    @Test
    fun should_pass_with_a_Number_other_than_zero() {
        val value: Int = Random.nonZeroInt()
        value.toNonZeroNumber()
            .getOrThrow()
            .value assertEquals value
    }

    @Test
    fun should_fail_with_a_Number_that_equals_zero() {
        val result: Result<NonZeroNumber<Int>> = 0.toNonZeroNumber()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}

class NonZeroNumberOfIntTimesNonZeroNumberOfIntTest {
    @Test
    fun should_pass() {
        val x: NonZeroNumber<Int> = Random.nonZeroNumberOfInt()
        val y: NonZeroNumber<Int> = Random.nonZeroNumberOfInt()
        val result: NonZeroNumber<Int> = x * y
        result.value assertEquals x.value * y.value
    }
}

class IntDivNonZeroNumberOfIntTest {
    @Test
    fun should_pass() {
        val x: Int = Random.nextInt()
        val y: NonZeroNumber<Int> = Random.nonZeroNumberOfInt()
        x / y assertEquals x / y.value
    }
}
