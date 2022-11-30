package kotools.types.experimental.number

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.random.Random
import kotlin.test.Test

@ExperimentalKotoolsTypesApi
class NonZeroIntTest {
    // ---------- Int.nonZero ----------

    @Test
    fun nonZero_should_pass_with_an_Int_other_than_zero() {
        val value: Int = Random.nextInt()
            .takeIf { it != 0 }
            ?: Random.nextInt()
        value.nonZero.getOrThrow().value assertEquals value
    }

    @Test
    fun nonZero_should_fail_with_an_Int_that_equals_zero() {
        assertFailsWith<NonZeroNumber.Exception>(0.nonZero::getOrThrow)
            .message.assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}

@ExperimentalKotoolsTypesApi
class NonZeroIntCompanionTest {
    @Test
    fun min_should_equal_the_minimum_value_of_Int(): Unit =
        NonZeroInt.min.value assertEquals Int.MIN_VALUE

    @Test
    fun max_should_equal_the_maximum_value_of_Int(): Unit =
        NonZeroInt.max.value assertEquals Int.MAX_VALUE

    @Test
    fun random_should_pass(): Unit = repeat(100) { NonZeroInt.random() }
}
