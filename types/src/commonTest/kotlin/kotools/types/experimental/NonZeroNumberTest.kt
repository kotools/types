package kotools.types.experimental

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotools.types.number.randomNonZeroInt
import kotlin.test.Test

@ExperimentalKotoolsTypesApi
class NonZeroNumberTest {
    // ---------- Byte.nonZero ----------

    @Test
    fun nonZero_should_pass_with_a_Byte_other_than_zero(): Unit =
        randomNonZeroInt()
            .value
            .toByte()
            .let { it.nonZero.getOrThrow().value assertEquals it }

    @Test
    fun nonZero_should_fail_with_a_Byte_that_equals_zero(): Unit = 0.toByte()
        .nonZero
        .let { assertFailsWith<IllegalArgumentException>(it::getOrThrow) }
        .message
        .assertNotNull()
        .isNotBlank()
        .assertTrue()

    // ---------- Short.nonZero ----------

    @Test
    fun nonZero_should_pass_with_a_Short_other_than_zero(): Unit =
        randomNonZeroInt()
            .value
            .toShort()
            .let { it.nonZero.getOrThrow().value assertEquals it }

    @Test
    fun nonZero_should_fail_with_a_Short_that_equals_zero(): Unit = 0.toShort()
        .nonZero
        .let { assertFailsWith<IllegalArgumentException>(it::getOrThrow) }
        .message
        .assertNotNull()
        .isNotBlank()
        .assertTrue()

    // ---------- NonZeroNumber.toString() ----------

    @Test
    fun toString_should_behave_like_its_value(): Unit = randomNonZeroInt()
        .value
        .toByte()
        .nonZero
        .getOrThrow()
        .run { toString() assertEquals value.toString() }
}
