package kotools.types.experimental

import kotools.assert.assertEquals
import kotools.assert.assertFalse
import kotools.assert.assertTrue
import kotools.types.number.randomNonZeroInt
import kotlin.test.Test

@ExperimentalKotoolsTypesApi
class ExplicitNumberTest {
    private val randomExplicitNumber: ExplicitNumber<Int>
        get() = randomNonZeroInt()
            .value
            .toNonZeroNumber()
            .getOrThrow()

    // ---------- equals(Any?) ----------

    @Test
    fun equals_should_pass_with_another_ExplicitNumber_holding_the_same_type_and_value_of_number() {
        val x: ExplicitNumber<Int> = randomExplicitNumber
        val y: ExplicitNumber<Int> = x.value.toNonZeroNumber()
            .getOrThrow()
        assertTrue { x == y }
    }

    @Test
    fun equals_should_fail_with_another_ExplicitNumber_holding_a_different_type_of_number() {
        val x: ExplicitNumber<Int> = randomExplicitNumber
        val y: ExplicitNumber<Float> = x.value.toFloat()
            .toNonZeroNumber()
            .getOrThrow()
        assertFalse { x == y }
    }

    @Test
    fun equals_should_fail_with_another_ExplicitNumber_holding_the_same_type_of_number_having_a_different_value(): Unit =
        assertFalse { randomExplicitNumber == randomExplicitNumber }

    // ---------- hashCode() ----------

    @Test
    fun hashCode_should_behave_like_its_value(): Unit =
        randomExplicitNumber.run { hashCode() assertEquals value.hashCode() }

    // ---------- toString() ----------

    @Test
    fun toString_should_behave_like_its_value(): Unit =
        randomExplicitNumber.run { toString() assertEquals value.toString() }
}
