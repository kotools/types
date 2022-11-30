package kotools.types.number

import kotools.assert.*
import kotlin.random.Random
import kotlin.test.Test

class NumberToNonZeroNumberTest {
    @Test
    fun should_pass_with_a_Number_other_than_zero() {
        val value: Int = Random.nonZeroInt().value
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

class RandomNonZeroIntTest {
    @Test
    fun should_pass(): Unit =
        Random.nonZeroInt().value assertNotEquals Random.nonZeroInt().value
}

class NonZeroNumberOfIntTimesNonZeroNumberOfIntTest {
    @Test
    fun should_pass() {
        val x: NonZeroNumber<Int> = Random.nonZeroInt()
        val y: NonZeroNumber<Int> = Random.nonZeroInt()
        val result: NonZeroNumber<Int> = x * y
        result.value assertEquals x.value * y.value
    }
}

class IntDivNonZeroNumberOfIntTest {
    @Test
    fun should_pass() {
        val x: Int = Random.nextInt()
        val y: NonZeroNumber<Int> = Random.nonZeroInt()
        x / y assertEquals x / y.value
    }
}
