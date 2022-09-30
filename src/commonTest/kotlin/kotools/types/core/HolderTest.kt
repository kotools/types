package kotools.types.core

import kotools.assert.assertEquals
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HolderTest : Randomizer {
    // ---------- Comparisons ----------

    @Test
    fun equals_should_return_true_with_another_Holder_holding_the_same_value() {
        // GIVEN
        val value: Int = randomInt
        val x: Holder<Int> = holderOf(value)
        val y: Holder<Int> = holderOf(value)
        // WHEN & THEN
        assertTrue { x == y }
    }

    @Test
    fun equals_should_return_false_with_another_Holder_holding_another_value_with_the_same_type() {
        // GIVEN
        val x: Holder<Int> = holderOf(randomInt)
        val y: Holder<Int> = holderOf(randomInt)
        // WHEN & THEN
        assertFalse { x == y }
    }

    @Test
    fun equals_should_return_false_with_another_Holder_holding_another_value_with_another_type() {
        // GIVEN
        val x: Holder<Int> = holderOf(randomInt)
        val y: Holder<String> = holderOf(randomString)
        // WHEN & THEN
        assertFalse { x == y }
    }

    @Test
    fun hashCode_should_return_the_hash_code_of_the_value() {
        // GIVEN
        val holder: Holder<Int> = holderOf(randomInt)
        // WHEN
        val result: Int = holder.hashCode()
        // THEN
        result assertEquals holder.value.hashCode()
    }

    // ---------- Conversions ----------

    @Test
    fun toString_should_return_the_string_representation_of_the_value() {
        // GIVEN
        val holder: Holder<Int> = holderOf(randomInt)
        // WHEN
        val result: String = holder.toString()
        // THEN
        result assertEquals holder.value.toString()
    }
}
