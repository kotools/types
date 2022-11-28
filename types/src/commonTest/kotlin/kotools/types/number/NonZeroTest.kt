package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.random.Random
import kotlin.test.Test

class NumberToNonZeroNumberTest {
    @Test
    fun should_pass_with_a_Number_other_than_zero() {
        val value: Int = Random.nextInt()
            .takeIf { it != 0 }
            ?: Random.nextInt()
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

class IntDivNonZeroNumberOfIntTest {
    @Test
    fun should_pass() {
        val x: Int = Random.nextInt()
        val value: Int = (Random.nextInt()
            .takeIf { it != 0 }
            ?: Random.nextInt())
        val y: NonZeroNumber<Int> = value.toNonZeroNumber()
            .getOrThrow()
        x / y assertEquals x / y.value
    }
}
