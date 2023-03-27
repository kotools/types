package kotools.types.result

import kotools.types.number.NonZeroInt
import kotools.types.number.ZeroInt
import kotools.types.shouldEqual
import kotools.types.shouldHaveAMessage
import kotools.types.text.NotBlankString
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ResultContextTest {
    @Test
    fun number_toNonZeroInt_should_pass_with_an_Int_other_than_zero() {
        val value: Number = NonZeroInt.random()
            .toInt()
        val result: Result<NonZeroInt> = resultOf { value.toNonZeroInt() }
        result.getOrThrow()
            .toInt() shouldEqual value
    }

    @Test
    fun number_toNonZeroInt_should_fail_with_an_Int_that_equals_zero() {
        val value: Number = ZeroInt.toInt()
        val result: Result<NonZeroInt> = resultOf { value.toNonZeroInt() }
        val exception: IllegalArgumentException =
            assertFailsWith(block = result::getOrThrow)
        exception.shouldHaveAMessage()
    }

    @Test
    fun string_toNotBlankString_should_pass_with_a_not_blank_String() {
        val string = "hello world"
        val result: Result<NotBlankString> = resultOf {
            string.toNotBlankString()
        }
        "${result.getOrThrow()}" shouldEqual string
    }

    @Test
    fun string_toNotBlankString_should_fail_with_a_blank_String() {
        val result: Result<NotBlankString> = resultOf { "".toNotBlankString() }
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .shouldHaveAMessage()
    }

    @Test
    fun resultOf_should_pass() {
        val value: Int = Random.nextInt()
        val result: Result<Int> = resultOf { value }
        result.getOrThrow() shouldEqual value
    }
}
