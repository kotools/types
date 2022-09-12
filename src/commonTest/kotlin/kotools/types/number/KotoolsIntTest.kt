package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertTrue
import kotlin.random.Random
import kotlin.test.Test

fun Int.toKotoolsIntExample(): KotoolsIntExample = KotoolsIntExample(this)

data class KotoolsIntExample(override val value: Int) : KotoolsInt

class KotoolsIntTest {
    // ---------- Binary operations ----------

    @Test
    fun plus_should_pass_with_an_Int() {
        val x: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        val y: Int = Random.nextInt()
        val result: Int = x + y
        result assertEquals x.value + y
    }

    @Test
    fun plus_should_pass_when_adding_a_KotoolsInt_to_an_Int() {
        val x: Int = Random.nextInt()
        val y: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        val result: Int = x + y
        result assertEquals x + y.value
    }

    @Test
    fun plus_should_pass_with_a_KotoolsInt() {
        val x: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        val y: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        val result: Int = x + y
        result assertEquals x.value + y.value
    }

    @Test
    fun minus_should_pass_with_an_Int() {
        val x: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        val y: Int = Random.nextInt()
        val result: Int = x - y
        result assertEquals x.value - y
    }

    @Test
    fun minus_should_pass_with_a_KotoolsInt() {
        val x: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        val y: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        val result: Int = x - y
        result assertEquals x.value - y.value
    }

    @Test
    fun minus_should_pass_when_subtracting_a_KotoolsInt_from_an_Int() {
        val x: Int = Random.nextInt()
        val y: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        val result: Int = x - y
        result assertEquals x - y.value
    }

    @Test
    fun times_should_pass_with_an_Int() {
        val x: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        val y: Int = Random.nextInt()
        val result: Int = x * y
        result assertEquals x.value * y
    }

    @Test
    fun times_should_pass_with_a_KotoolsInt() {
        val x: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        val y: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        val result: Int = x * y
        result assertEquals x.value * y.value
    }

    @Test
    fun times_should_pass_when_multiplying_an_Int_by_a_KotoolsInt() {
        val x: Int = Random.nextInt()
        val y: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        val result: Int = x * y
        result assertEquals x * y.value
    }

    @Test
    fun div_should_pass_with_an_Int_other_than_0() {
        val x: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        var y = 0
        while (y == 0) y = Random.nextInt()
        val result: Int = x / y
        result assertEquals x.value / y
    }

    @Test
    fun div_should_pass_when_dividing_an_Int_by_a_KotoolsInt_other_than_0() {
        val x: Int = Random.nextInt()
        var y: KotoolsInt = KotoolsIntExample(0)
        while (y.value == 0) y = Random.nextInt().toKotoolsIntExample()
        val result: Int = x / y
        result assertEquals x / y.value
    }

    @Test
    fun div_should_pass_with_a_KotoolsInt_other_than_0() {
        val x: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        var y: KotoolsInt = KotoolsIntExample(0)
        while (y.value == 0) y = Random.nextInt().toKotoolsIntExample()
        val result: Int = x / y
        result assertEquals x.value / y.value
    }

    // ---------- Comparisons ----------

    @Test
    fun compareTo_should_return_0_with_the_same_Int(): Unit = Random.nextInt()
        .toKotoolsIntExample()
        .run { this compareTo value assertEquals 0 }

    @Test
    fun compareTo_should_return_a_negative_number_with_a_greater_Int() {
        val x: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        var y: Int = Random.nextInt()
        while (x >= y) y = Random.nextInt()
        val result: Int = x compareTo y
        assertTrue { result < 0 }
    }

    @Test
    fun compareTo_should_return_a_positive_number_with_a_less_Int() {
        val x: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        var y: Int = Random.nextInt()
        while (x <= y) y = Random.nextInt()
        val result: Int = x compareTo y
        assertTrue { result > 0 }
    }

    @Test
    fun compareTo_should_return_0_when_comparing_an_Int_with_the_same_KotoolsInt(): Unit =
        Random.nextInt()
            .run { this compareTo toKotoolsIntExample() assertEquals 0 }

    @Test
    fun compareTo_should_return_a_negative_number_when_comparing_an_Int_with_a_greater_KotoolsInt() {
        val x: Int = Random.nextInt()
        var y: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        while (x >= y) y = Random.nextInt().toKotoolsIntExample()
        val result: Int = x compareTo y
        assertTrue { result < 0 }
    }

    @Test
    fun compareTo_should_return_a_positive_number_when_comparing_an_Int_with_a_less_KotoolsInt() {
        val x: Int = Random.nextInt()
        var y: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        while (x <= y) y = Random.nextInt().toKotoolsIntExample()
        val result: Int = x compareTo y
        assertTrue { result > 0 }
    }

    @Test
    fun compareTo_should_return_0_with_the_same_KotoolsInt(): Unit =
        Random.nextInt()
            .toKotoolsIntExample()
            .let { it compareTo it assertEquals 0 }

    @Test
    fun compareTo_should_return_a_negative_number_with_a_greater_KotoolsInt() {
        val x: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        var y: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        while (x >= y) y = Random.nextInt().toKotoolsIntExample()
        val result: Int = x compareTo y
        assertTrue { result < 0 }
    }

    @Test
    fun compareTo_should_return_a_positive_number_with_a_less_KotoolsInt() {
        val x: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        var y: KotoolsInt = Random.nextInt().toKotoolsIntExample()
        while (x <= y) y = Random.nextInt().toKotoolsIntExample()
        val result: Int = x compareTo y
        assertTrue { result > 0 }
    }

    // ---------- Conversions ----------

    @Test
    fun toNotBlankString_should_pass(): Unit =
        Random.nextInt().toKotoolsIntExample().run {
            toNotBlankString().value assertEquals value.toString()
        }
}