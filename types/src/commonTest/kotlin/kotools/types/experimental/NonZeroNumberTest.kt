package kotools.types.experimental

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotools.types.number.randomNonZeroInt
import kotlin.test.Test

@ExperimentalKotoolsTypesApi
class NonZeroNumberTest {
    // ---------- Number.toNonZeroNumber() ----------

    @Test
    fun toNonZeroNumber_should_pass_with_a_Number_other_than_zero() {
        val value: Int = randomNonZeroInt().value
        value.toNonZeroNumber()
            .getOrThrow()
            .value assertEquals value
    }

    @Test
    fun toNonZeroNumber_should_fail_with_a_Number_that_equals_zero() {
        val result: Result<NonZeroNumber<Int>> = 0.toNonZeroNumber()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    // ---------- NonZeroNumber.toString() ----------

    @Test
    fun toString_should_behave_like_its_value(): Unit = randomNonZeroInt()
        .value
        .toNonZeroNumber()
        .getOrThrow()
        .run { toString() assertEquals value.toString() }
}
