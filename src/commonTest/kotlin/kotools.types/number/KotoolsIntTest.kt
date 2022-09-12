package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertTrue
import kotlin.random.Random
import kotlin.test.Ignore
import kotlin.test.Test

private fun Int.toExampleInt(): ExampleInt = ExampleInt(this)

private data class ExampleInt(override val value: Int) : KotoolsInt

class KotoolsIntTest {
    // ---------- Binary operations ----------

    @Test
    fun plus_should_pass_with_an_Int() {
        // GIVEN
        val x: KotoolsInt = Random.nextInt().toExampleInt()
        val y: Int = Random.nextInt()
        // WHEN
        val result: Int = x + y
        // THEN
        result assertEquals x.value + y
    }

    @Test
    fun plus_should_pass_when_adding_a_KotoolsInt_to_an_Int() {
        // GIVEN
        val x: Int = Random.nextInt()
        val y: KotoolsInt = Random.nextInt().toExampleInt()
        // WHEN
        val result: Int = x + y
        // THEN
        result assertEquals x + y.value
    }

    @Test
    fun plus_should_pass_with_a_KotoolsInt() {
        // GIVEN
        val x: KotoolsInt = Random.nextInt().toExampleInt()
        val y: KotoolsInt = Random.nextInt().toExampleInt()
        // WHEN
        val result: Int = x + y
        // THEN
        result assertEquals x.value + y.value
    }

    @Test
    fun minus_should_pass_with_an_Int() {
        // GIVEN
        val x: KotoolsInt = Random.nextInt().toExampleInt()
        val y: Int = Random.nextInt()
        // WHEN
        val result: Int = x - y
        // THEN
        result assertEquals x.value - y
    }

    @Test
    fun minus_should_pass_with_a_KotoolsInt() {
        // GIVEN
        val x: KotoolsInt = Random.nextInt().toExampleInt()
        val y: KotoolsInt = Random.nextInt().toExampleInt()
        // WHEN
        val result: Int = x - y
        // THEN
        result assertEquals x.value - y.value
    }

    @Test
    fun minus_should_pass_when_subtracting_a_KotoolsInt_from_an_Int() {
        // GIVEN
        val x: Int = Random.nextInt()
        val y: KotoolsInt = Random.nextInt().toExampleInt()
        // WHEN
        val result: Int = x - y
        // THEN
        result assertEquals x - y.value
    }

    @Test
    fun times_should_pass_with_an_Int() {
        // GIVEN
        val x: KotoolsInt = Random.nextInt().toExampleInt()
        val y: Int = Random.nextInt()
        // WHEN
        val result: Int = x * y
        // THEN
        result assertEquals x.value * y
    }

    @Test
    fun times_should_pass_with_a_KotoolsInt() {
        // GIVEN
        val x: KotoolsInt = Random.nextInt().toExampleInt()
        val y: KotoolsInt = Random.nextInt().toExampleInt()
        // WHEN
        val result: Int = x * y
        // THEN
        result assertEquals x.value * y.value
    }

    @Test
    fun times_should_pass_when_multiplying_an_Int_by_a_KotoolsInt() {
        // GIVEN
        val x: Int = Random.nextInt()
        val y: KotoolsInt = Random.nextInt().toExampleInt()
        // WHEN
        val result: Int = x * y
        // THEN
        result assertEquals x * y.value
    }

    @Test
    fun div_should_pass_with_an_Int_other_than_0() {
        // GIVEN
        val x: KotoolsInt = Random.nextInt().toExampleInt()
        var y = 0
        while (y == 0) y = Random.nextInt()
        // WHEN
        val result: Int = x / y
        // THEN
        result assertEquals x.value / y
    }

    // TODO: This function should be platform-specific.
    @Ignore
    @Test
    fun div_should_throw_an_ArithmeticException_with_an_Int_that_equals_0() {
        // GIVEN
        val x: KotoolsInt = Random.nextInt().toExampleInt()
        val y = 0
        // WHEN & THEN
        assertFailsWith<ArithmeticException> { x / y }
    }

    @Test
    fun div_should_pass_when_dividing_an_Int_by_a_KotoolsInt_other_than_0() {
        // GIVEN
        val x: Int = Random.nextInt()
        var y: KotoolsInt = ExampleInt(0)
        while (y.value == 0) y = Random.nextInt().toExampleInt()
        // WHEN
        val result: Int = x / y
        // THEN
        result assertEquals x / y.value
    }

    // TODO: This function should be platform-specific.
    @Ignore
    @Test
    fun div_should_throw_an_ArithmeticException_when_dividing_an_Int_by_a_KotoolsInt_that_equals_0() {
        // GIVEN
        val x: Int = Random.nextInt()
        val y: KotoolsInt = ExampleInt(0)
        // WHEN & THEN
        assertFailsWith<ArithmeticException> { x / y }
    }

    @Test
    fun div_should_pass_with_a_KotoolsInt_other_than_0() {
        // GIVEN
        val x: KotoolsInt = Random.nextInt().toExampleInt()
        var y: KotoolsInt = ExampleInt(0)
        while (y.value == 0) y = Random.nextInt().toExampleInt()
        // WHEN
        val result: Int = x / y
        // THEN
        result assertEquals x.value / y.value
    }

    // TODO: This function should be platform-specific.
    @Ignore
    @Test
    fun div_should_throw_an_ArithmeticException_with_a_KotoolsInt_that_equals_0() {
        // GIVEN
        val x: KotoolsInt = Random.nextInt().toExampleInt()
        val y = ExampleInt(0)
        // WHEN & THEN
        assertFailsWith<ArithmeticException> { x / y }
    }

    // ---------- Comparisons ----------

    @Test
    fun compareTo_should_return_0_with_the_same_Int() {
        // GIVEN
        val x: KotoolsInt = Random.nextInt().toExampleInt()
        // WHEN
        val result: Int = x compareTo x.value
        // THEN
        result assertEquals 0
    }

    @Test
    fun compareTo_should_return_a_negative_number_with_a_greater_Int() {
        // GIVEN
        val x: KotoolsInt = Random.nextInt().toExampleInt()
        var y: Int = Random.nextInt()
        while (x >= y) y = Random.nextInt()
        // WHEN
        val result: Int = x compareTo y
        // THEN
        assertTrue { result < 0 }
    }

    @Test
    fun compareTo_should_return_a_positive_number_with_a_less_Int() {
        // GIVEN
        val x: KotoolsInt = Random.nextInt().toExampleInt()
        var y: Int = Random.nextInt()
        while (x <= y) y = Random.nextInt()
        // WHEN
        val result: Int = x compareTo y
        // THEN
        assertTrue { result > 0 }
    }

    @Test
    fun compareTo_should_return_0_when_comparing_an_Int_with_the_same_KotoolsInt() {
        // GIVEN
        val x: Int = Random.nextInt()
        val y: KotoolsInt = x.toExampleInt()
        // WHEN
        val result: Int = x compareTo y
        // THEN
        result assertEquals 0
    }

    @Test
    fun compareTo_should_return_a_negative_number_when_comparing_an_Int_with_a_greater_KotoolsInt() {
        // GIVEN
        val x: Int = Random.nextInt()
        var y: KotoolsInt = Random.nextInt().toExampleInt()
        while (x >= y) y = Random.nextInt().toExampleInt()
        // WHEN
        val result: Int = x compareTo y
        // THEN
        assertTrue { result < 0 }
    }

    @Test
    fun compareTo_should_return_a_positive_number_when_comparing_an_Int_with_a_less_KotoolsInt() {
        // GIVEN
        val x: Int = Random.nextInt()
        var y: KotoolsInt = Random.nextInt().toExampleInt()
        while (x <= y) y = Random.nextInt().toExampleInt()
        // WHEN
        val result: Int = x compareTo y
        // THEN
        assertTrue { result > 0 }
    }

    @Test
    fun compareTo_should_return_0_with_the_same_KotoolsInt() {
        // GIVEN
        val x: KotoolsInt = Random.nextInt().toExampleInt()
        // WHEN
        val result: Int = x compareTo x
        // THEN
        result assertEquals 0
    }

    @Test
    fun compareTo_should_return_a_negative_number_with_a_greater_KotoolsInt() {
        // GIVEN
        val x: KotoolsInt = Random.nextInt().toExampleInt()
        var y: KotoolsInt = Random.nextInt().toExampleInt()
        while (x >= y) y = Random.nextInt().toExampleInt()
        // WHEN
        val result: Int = x compareTo y
        // THEN
        assertTrue { result < 0 }
    }

    @Test
    fun compareTo_should_return_a_positive_number_with_a_less_KotoolsInt() {
        // GIVEN
        val x: KotoolsInt = Random.nextInt().toExampleInt()
        var y: KotoolsInt = Random.nextInt().toExampleInt()
        while (x <= y) y = Random.nextInt().toExampleInt()
        // WHEN
        val result: Int = x compareTo y
        // THEN
        assertTrue { result > 0 }
    }
}
