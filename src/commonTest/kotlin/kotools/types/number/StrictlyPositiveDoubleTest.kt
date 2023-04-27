package kotools.types.number

import kotools.types.shouldEqual
import kotools.types.shouldHaveAMessage
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class StrictlyPositiveDoubleTest {
    @Test
    fun compareTo_should_return_zero_with_the_same_StrictlyPositiveDouble() {
        val x: StrictlyPositiveDouble = Random.nextDouble()
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val y: StrictlyPositiveDouble = x.toDouble()
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val result: Int = x.compareTo(y)
        result shouldEqual 0
    }

    @Test
    fun compareTo_should_return_a_negative_Int_with_a_greater_StrictlyPositiveDouble() {
        val x: StrictlyPositiveDouble = Random.nextDouble(until = 0.5)
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val y: StrictlyPositiveDouble = Random
            .nextDouble(from = 0.6, until = Double.MAX_VALUE)
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val result: Int = x.compareTo(y)
        assertTrue(result < 0)
    }

    @Test
    fun compareTo_should_return_a_positive_Int_with_a_lower_StrictlyPositiveDouble() {
        val x: StrictlyPositiveDouble = Random
            .nextDouble(from = 0.6, until = Double.MAX_VALUE)
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val y: StrictlyPositiveDouble = Random.nextDouble(until = 0.5)
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val result: Int = x.compareTo(y)
        assertTrue(result > 0)
    }

    @Test
    fun toString_should_return_the_string_representation_of_its_value() {
        val value: Double = Random.nextDouble()
        value.toStrictlyPositiveDouble()
            .getOrThrow()
            .toString()
            .shouldEqual("$value")
    }

    @Test
    fun number_toStrictlyPositiveDouble_should_pass_with_a_strictly_positive_Number() {
        val value: Number =
            Random.nextDouble(from = 0.1, until = Double.MAX_VALUE)
        val result: Result<StrictlyPositiveDouble> =
            value.toStrictlyPositiveDouble()
        result.getOrThrow()
            .toDouble()
            .shouldEqual(value)
    }

    @Test
    fun number_toStrictlyPositiveDouble_should_fail_with_a_negative_Number(): Unit =
        Random.nextDouble(from = 0.1, until = Double.MAX_VALUE)
            .unaryMinus()
            .toStrictlyPositiveDouble()
            .run {
                assertFailsWith<IllegalArgumentException>(block = ::getOrThrow)
            }
            .shouldHaveAMessage()
}
