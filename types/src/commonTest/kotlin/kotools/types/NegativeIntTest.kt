package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test

class NegativeIntTest {
    @Test
    fun toString_should_pass() {
        val value: Int = Random.nextInt(NegativeInt.range)
        value.toNegativeInt()
            .getOrThrow()
            .toString() assertEquals "$value"
    }

    @Test
    fun int_toNegativeInt_should_pass_with_a_negative_Int() {
        val value: Int = Random.nextInt(NegativeInt.range)
        value.toNegativeInt()
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun int_toNegativeInt_should_fail_with_a_strictly_positive_Int() {
        val result: Result<NegativeInt> = Random.nextInt(1..Int.MAX_VALUE)
            .toNegativeInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}

private val NegativeInt.Companion.range: IntRange by lazy { Int.MIN_VALUE..0 }
