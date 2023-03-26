package kotools.types.result

import kotools.types.shouldEqual
import kotools.types.shouldHaveAMessage
import kotools.types.text.NotBlankString
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ResultContextTest {
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
